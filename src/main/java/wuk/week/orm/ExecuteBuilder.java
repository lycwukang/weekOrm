package wuk.week.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class ExecuteBuilder<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteBuilder.class);

    // ======= 此次操作的数据源
    private DataSource dataSource;
    // ======= 事务控制器
    private WeekTransaction transaction;
    // ======= 此次操作sql的主体对象，orm对象
    private Class<T> clazz;
    // ======= bean声明数据
    private BeanDeclare<T> beanDeclare;
    // ======= 操作sql的类型，select/insert/update/delete
    private ExecuteType type = null;
    // ======= on duplicate key update，只在insert时使用
    private boolean onDuplicateKeyUpdate = false;
    // ======= where条件，只在select/update/delete时使用
    private Where where = new WhereEmpty();
    // ======= select返回值，只在select时使用
    private List<Column> selects = new ArrayList<>();
    // ======= group by条件，只在select时使用
    private List<Field> groupBys = new ArrayList<>();
    // ======= order by条件，只在select时使用
    private List<Pair<Field, OrderType>> orderBys = new ArrayList<>();
    // ======= having条件，只在select时使用
    private HavingEmpty having = new HavingEmpty();
    // ======= 更新数据
    private List<Pair<Field, Object>> setFields = new ArrayList<>();
    // ======= limit #{start}, #{size}，只在select时使用
    private int limitStart = 0;
    private int limitSize = 0;

    /////////////////////////////////// 临时变量
    private JoinType joinType = JoinType.and;

    public ExecuteBuilder(DataSource dataSource, WeekTransaction transaction, Class<T> clazz) {
        this.dataSource = dataSource;
        this.transaction = transaction;
        this.clazz = clazz;
        this.beanDeclare = BeanDeclare.findDeclare(clazz);
    }

    public ExecuteBuilder<T> select() {
        type = ExecuteType.select;
        return this;
    }

    public ExecuteBuilder<T> select(Field... fields) {
        type = ExecuteType.select;
        for (Field field : fields) {
            selects.add(ColumnHelper.field(field));
        }
        return this;
    }

    public ExecuteBuilder<T> select(Column... columns) {
        type = ExecuteType.select;
        Collections.addAll(selects, columns);
        return this;
    }

    public ExecuteBuilder<T> insert() {
        type = ExecuteType.insert;
        return this;
    }

    public ExecuteBuilder<T> onDuplicateKeyUpdate() {
        onDuplicateKeyUpdate = true;
        return this;
    }

    public ExecuteBuilder<T> update() {
        type = ExecuteType.update;
        return this;
    }

    public ExecuteBuilder<T> delete() {
        type = ExecuteType.delete;
        return this;
    }

    public ExecuteBuilder<T> where() {
        where = new WhereEmpty();
        return this;
    }

    public ExecuteBuilder<T> set(Field field, Object value) {
        this.setFields.add(new Pair<>(field, value));
        return this;
    }

    public ExecuteBuilder<T> and() {
        joinType = JoinType.and;
        return this;
    }

    public ExecuteBuilder<T> or() {
        joinType = JoinType.or;
        return this;
    }

    public ExecuteBuilder<T> eq(Field field, Object value) {
        return add(WhereHelper.eq(field, value));
    }

    public ExecuteBuilder<T> notEq(Field field, Object value) {
        return add(WhereHelper.notEq(field, value));
    }

    public ExecuteBuilder<T> lt(Field field, Object value) {
        return add(WhereHelper.lt(field, value));
    }

    public ExecuteBuilder<T> ltEq(Field field, Object value) {
        return add(WhereHelper.ltEq(field, value));
    }

    public ExecuteBuilder<T> gt(Field field, Object value) {
        return add(WhereHelper.gt(field, value));
    }

    public ExecuteBuilder<T> gtEq(Field field, Object value) {
        return add(WhereHelper.gtEq(field, value));
    }

    public ExecuteBuilder<T> in(Field field, Object... values) {
        return add(WhereHelper.in(field, values));
    }

    public ExecuteBuilder<T> notIn(Field field, Object... values) {
        return add(WhereHelper.notIn(field, values));
    }

    public ExecuteBuilder<T> like(Field field, String value) {
        return add(WhereHelper.like(field, value));
    }

    public ExecuteBuilder<T> groupBy(Field... fields) {
        Collections.addAll(groupBys, fields);
        return this;
    }

    public ExecuteBuilder<T> having(Column column, OperatorType operatorType, Object value) {
        addHaving(new HavingImpl(column, operatorType, value));
        return this;
    }

    public ExecuteBuilder<T> havingEq(MethodType methodType, Field field, Object value) {
        addHaving(HavingHelper.eq(field, methodType, value));
        return this;
    }

    public ExecuteBuilder<T> havingNotEq(MethodType methodType, Field field, Object value) {
        addHaving(HavingHelper.notEq(field, methodType, value));
        return this;
    }

    public ExecuteBuilder<T> havingLt(MethodType methodType, Field field, Object value) {
        addHaving(HavingHelper.lt(field, methodType, value));
        return this;
    }

    public ExecuteBuilder<T> havingLtEq(MethodType methodType, Field field, Object value) {
        addHaving(HavingHelper.ltEq(field, methodType, value));
        return this;
    }

    public ExecuteBuilder<T> havingGt(MethodType methodType, Field field, Object value) {
        addHaving(HavingHelper.gt(field, methodType, value));
        return this;
    }

    public ExecuteBuilder<T> havingGtEq(MethodType methodType, Field field, Object value) {
        addHaving(HavingHelper.gtEq(field, methodType, value));
        return this;
    }

    public ExecuteBuilder<T> orderBy(Field field, OrderType orderType) {
        orderBys.add(new Pair<>(field, orderType));
        return this;
    }

    public ExecuteBuilder<T> limit(int size) {
        this.limitSize = size;
        return this;
    }

    public ExecuteBuilder<T> limit(int start, int size) {
        this.limitStart = start;
        this.limitSize = size;
        return this;
    }

    private ExecuteBuilder<T> add(Where w) {
        if (joinType.equals(JoinType.and)) {
            where.and(w);
        } else {
            where.or(w);
        }
        return this;
    }

    private ExecuteBuilder<T> addHaving(Where w) {
        if (joinType.equals(JoinType.and)) {
            having.and(w);
        } else {
            having.or(w);
        }
        return this;
    }

    public int exec() {
        if (ExecuteType.update.equals(type)) {
            return updateExec();
        } else if (ExecuteType.delete.equals(type)) {
            return deleteExec();
        } else {
            throw new RuntimeException("此方法只支持update和delete操作");
        }
    }

    public int exec(T obj) {
        return exec(obj, null);
    }

    public int exec(T obj, WeekGeneratedKey generatedKey) {
        if (!ExecuteType.insert.equals(type)) {
            throw new RuntimeException("此方法只支持insert操作");
        }
        return insertExec(obj, generatedKey);
    }

    private int deleteExec() {
        StringBuilder builder = new StringBuilder();
        builder.append("delete from ").append("`").append(beanDeclare.getTableName()).append("`");

        where.builder(builder);
        List<Pair<Field, Object>> params = where.builderObj();
        if (params.size() == 0) {
            throw new RuntimeException("请设置where条件");
        }

        String sql = builder.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("[week-orm]执行sql:" + sql);
        }

        return runAndReturnRows(sql, params, null);
    }

    private int updateExec() {
        StringBuilder builder = new StringBuilder();
        builder.append("update ").append("`").append(beanDeclare.getTableName()).append("`");

        if (setFields.size() == 0) {
            throw new RuntimeException("请设置set条件");
        }

        List<Pair<Field, Object>> params = new ArrayList<>();
        for (int i = 0; i < setFields.size(); i++) {
            if (i == 0) {
                builder.append("set ");
            } else {
                builder.append(", ");
            }
            Field field = setFields.get(i).getT();
            Object value = setFields.get(i).getF();
            builder.append("`").append(beanDeclare.getColumnName(field)).append("`").append(" = ").append("?");
            params.add(new Pair<>(field, value));
        }

        where.builder(builder);
        List<Pair<Field, Object>> whereParams = where.builderObj();
        if (whereParams.size() == 0) {
            throw new RuntimeException("请设置where条件");
        }
        params.addAll(whereParams);

        String sql = builder.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("[week-orm]执行sql:" + sql);
        }

        return runAndReturnRows(sql, params, null);
    }

    private int insertExec(T obj, WeekGeneratedKey generatedKey) {
        StringBuilder builder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        List<Pair<Field, Object>> params = new ArrayList<>();

        builder.append("insert into ").append("`").append(beanDeclare.getTableName()).append("`");
        builder.append("(");
        valueBuilder.append("(");
        for (int i = 0; i < beanDeclare.getFields().size(); i++) {
            Field field = beanDeclare.getFields().get(i);
            Object value = beanDeclare.getFieldValue(field, obj);

            boolean append = true;
            if (beanDeclare.isAutoIncrement(field)) {
                append = false;
            } else if (value == null) {
                if (beanDeclare.isNotNull(field) && !beanDeclare.isDefaultValue(field)) {
                    throw new RuntimeException("尝试给不为空的字段传递空值[class=" + clazz.getName() + ", field=" + field.getName() + "]");
                }
                append = false;
            }

            if (append) {
                if (params.size() > 0) {
                    builder.append(", ");
                    valueBuilder.append(", ");
                }
                builder.append("`").append(beanDeclare.getColumnName(field)).append("`");
                valueBuilder.append("?");
                params.add(new Pair<>(field, value));
            }
        }
        builder.append(")");
        valueBuilder.append(")");
        builder.append(" values ").append(valueBuilder.toString());

        if (onDuplicateKeyUpdate) {
            if (setFields.size() == 0) {
                throw new RuntimeException("on duplicate key update需要设置更新数据");
            }

            builder.append(" on duplicate key update ");

            for (int i = 0; i < setFields.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                Field field = setFields.get(i).getT();
                Object value = setFields.get(i).getF();
                builder.append("`").append(beanDeclare.getColumnName(field)).append("`").append(" = ").append("?");
                params.add(new Pair<>(field, value));
            }
        }

        String sql = builder.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("[week-orm]执行sql:" + sql);
        }

        return runAndReturnRows(sql, params, generatedKey);
    }

    public T read() {
        return read(clazz);
    }

    public List<T> readList() {
        return readList(clazz);
    }

    public <F> F read(Class<F> returnClazz) {
        List<F> list = readList(returnClazz, true);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public <F> List<F> readList(Class<F> returnClazz) {
        return readList(returnClazz, false);
    }

    private <F> List<F> readList(Class<F> returnClazz, boolean returnOne) {
        StringBuilder builder = new StringBuilder();

        builder.append("select ");
        if (selects.size() > 0) {
            for (int i = 0; i < selects.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                ColumnHelper.builder(builder, beanDeclare, selects.get(i));
            }
        } else {
            List<Field> fields = beanDeclare.getFields();
            for (int i = 0; i < fields.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("`").append(beanDeclare.getColumnName(fields.get(i))).append("`");
            }
        }
        builder.append(" from ").append("`").append(beanDeclare.getTableName()).append("`");

        where.builder(builder);
        List<Pair<Field, Object>> params = where.builderObj();

        if (groupBys.size() > 0) {
            builder.append(" group by ");
            for (int i = 0; i < groupBys.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("`").append(beanDeclare.getColumnName(groupBys.get(i))).append("`");
            }
        }

        having.builder(builder);
        params.addAll(having.builderObj());

        if (orderBys.size() > 0) {
            builder.append(" order by ");
            for (int i = 0; i < orderBys.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("`").append(beanDeclare.getColumnName(orderBys.get(i).getT())).append("`");
                builder.append(orderBys.get(i).getF().equals(OrderType.asc) ? " asc" : " desc");
            }
        }

        if (limitStart > 0 || limitSize > 0) {
            builder.append(" limit ").append(limitStart);
            if (limitSize > 0) {
                builder.append(", ").append(limitSize);
            }
        }

        String sql = builder.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("[week-orm]执行sql:" + sql);
        }

        Connection connection = null;
        try {
            if (transaction != null) {
                connection = transaction.getConnection();
            } else {
                connection = dataSource.getConnection();
                connection.setAutoCommit(true);
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                beanDeclare.fillParams(statement, params);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (returnOne) {
                        return Collections.singletonList(ResultSetHelper.read(resultSet, returnClazz));
                    } else {
                        return ResultSetHelper.readList(resultSet, returnClazz);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("操作数据库出错", e);
        } finally {
            if (transaction == null && connection != null) {
                tryCloseConnection(connection);
            }
        }
    }

    private int runAndReturnRows(String sql, List<Pair<Field, Object>> params, WeekGeneratedKey generatedKey) {
        Connection connection = null;
        try {
            if (transaction != null) {
                connection = transaction.getConnection();
            } else {
                connection = dataSource.getConnection();
                connection.setAutoCommit(true);
            }
            int returnGeneratedKeys = generatedKey != null ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS;
            try (PreparedStatement statement = connection.prepareStatement(sql, returnGeneratedKeys)) {
                beanDeclare.fillParams(statement, params);
                int result = statement.executeUpdate();
                if (generatedKey != null) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    generatedKeys.next();
                    generatedKey.setKey(generatedKeys.getObject(1));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("操作数据库出错", e);
        } finally {
            if (transaction == null && connection != null) {
                tryCloseConnection(connection);
            }
        }
    }

    private void tryCloseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            if (logger.isErrorEnabled()) {
                logger.error("关闭数据库链接发生异常", e);
            }
        }
    }
}