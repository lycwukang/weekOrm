package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.bean.FastormBeanStructure;
import com.wuk.fastorm.exception.FastormException;
import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.*;
import com.wuk.fastorm.sql.expression.*;
import com.wuk.fastorm.sql.field.*;
import com.wuk.fastorm.sql.collection.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 创建sql的建造类
 * @param <T>
 */
public class FastormSqlBuilder<T> implements FastormSqlExecutor<T> {

    /**
     * class的BeanStructure实例
     */
    protected FastormBeanStructure<T> beanStructure;

    /**
     * 动态sql实例
     */
    protected StandardSql sql;

    /**
     * 等待写入的第一个对象
     */
    protected T insertFirstData;

    /**
     * 表达式链接类型
     */
    protected SqlJoinType joinType;

    /**
     * 添加表达式
     */
    protected Consumer<SqlExpression> joinSqlExpressionConsumer;

    /**
     * 保存包含表达式的状态
     */
    protected Queue<FastormGroupJoinSqlExpression> groupJoinSqlExpressionQueue = new LinkedBlockingQueue<>();

    /**
     * 执行器
     */
    protected FastormSqlExecutor<T> sqlExecutor;

    /**
     * select
     * @return
     */
    public FastormSqlBuilder<T> select() {
        SqlFieldCollection collection = new SqlFieldCollection(beanStructure.getFieldNames().size());
        for (String fieldName : beanStructure.getFieldNames()) {
            String columnName = beanStructure.findColumnName(fieldName);
            collection.add(new NameSqlField(columnName));
        }
        return select(collection);
    }

    /**
     * select
     * @param func
     * @return
     */
    public FastormSqlBuilder<T> select(Function<T, ?> func) {
        FuncCollection<T> collection = new FuncCollection<>(1);
        Collections.addAll(collection, func);

        return select(collection);
    }

    /**
     * select
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> select(FuncCollection<T> collection) {
        SqlFieldCollection fieldCollection = new SqlFieldCollection(collection.size());
        for (Function<T, ?> function : collection) {
            String columnName = beanStructure.findColumnName(function);
            fieldCollection.add(new NameSqlField(columnName));
        }

        return select(fieldCollection);
    }

    /**
     * select
     * @param field
     * @return
     */
    public FastormSqlBuilder<T> select(SqlField field) {
        SqlFieldCollection collection = new SqlFieldCollection(1);
        Collections.addAll(collection, field);
        return select(collection);
    }

    /**
     * select
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> select(SqlFieldCollection collection) {
        sql.setType(SqlType.SELECT);

        for (SqlField sqlField : collection) {
            sql.addField(sqlField);
        }

        return this;
    }

    /**
     * insert
     * @param t
     * @return
     */
    public FastormSqlBuilder<T> insert(T t) {
        return insert(Collections.singletonList(t));
    }

    /**
     * insert
     * @param ts
     * @return
     */
    public FastormSqlBuilder<T> insert(List<T> ts) {
        if (ts.size() == 0) {
            throw new FastormSqlException("insert的数据不能为空");
        }

        if (sql.getInsertFieldList().size() == 0) {
            for (String fieldName : beanStructure.getFieldNames()) {
                String columnName = beanStructure.findColumnName(fieldName);
                boolean autoIncrement = beanStructure.isAutoIncrement(fieldName);

                if (!autoIncrement) {
                    sql.addInsertFieldList(new NameSqlField(columnName));
                }
            }
        }

        for (T t : ts) {
            if (insertFirstData == null) {
                insertFirstData = t;
            }

            List<SqlParam> insertDataParams = new ArrayList<>(beanStructure.getFieldNames().size());
            for (String fieldName : beanStructure.getFieldNames()) {
                boolean autoIncrement = beanStructure.isAutoIncrement(fieldName);

                if (!autoIncrement) {
                    Method method = beanStructure.getReadMethodMap().get(fieldName);
                    Object obj;
                    try {
                        obj = method.invoke(t);
                    } catch (Exception e) {
                        throw new FastormSqlException(String.format("调用readMethod出错，class=%s，method=%s", beanStructure.getClazz().getName(), method.getName()));
                    }
                    insertDataParams.add(new SqlParam(method.getReturnType(), obj));
                }
            }
            sql.addInsertDataList(insertDataParams);
        }

        sql.setType(SqlType.INSERT);

        return this;
    }

    /**
     * on duplicate key update
     * @return
     */
    public FastormSqlBuilder<T> onDuplicateKeyUpdate() {
        sql.setOnDuplicateKeyUpdate(true);

        return this;
    }

    /**
     * update
     * @return
     */
    public FastormSqlBuilder<T> update() {
        sql.setType(SqlType.UPDATE);

        return this;
    }

    /**
     * delete
     * @return
     */
    public FastormSqlBuilder<T> delete() {
        sql.setType(SqlType.DELETE);

        return this;
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, Integer> func, Integer val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, Long> func, Long val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, Float> func, Float val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, Double> func, Double val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, Boolean> func, Boolean val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, String> func, String val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, Date> func, Date val) {
        return set(func, (Object) val);
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    private FastormSqlBuilder<T> set(Function<T, ?> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        sql.addSet(new StandardSqlSqlSet(columnName, new VariableSqlField(clazz, val)));

        return this;
    }

    /**
     * set #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> set(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        sql.addSet(new StandardSqlSqlSet(columnName, val));

        return this;
    }

    /**
     * where
     * @return
     */
    public FastormSqlBuilder<T> where() {
        joinType = SqlJoinType.EMPTY;
        joinSqlExpressionConsumer = sql::addWhere;

        return this;
    }

    /**
     * having
     * @return
     */
    public FastormSqlBuilder<T> having() {
        joinType = SqlJoinType.EMPTY;
        joinSqlExpressionConsumer = sql::addHaving;

        return this;
    }

    /**
     * 左括号
     * @return
     */
    public FastormSqlBuilder<T> left() {
        GroupJoinSqlExpression expressions = new GroupJoinSqlExpression();
        expressions.setJoinType(joinType);

        FastormGroupJoinSqlExpression joinSqlExpression = new FastormGroupJoinSqlExpression();
        joinSqlExpression.setConsumer(joinSqlExpressionConsumer);
        joinSqlExpression.setExpressions(expressions);

        groupJoinSqlExpressionQueue.offer(joinSqlExpression);
        joinSqlExpressionConsumer = expressions::add;
        joinType = SqlJoinType.EMPTY;

        return this;
    }

    /**
     * 右括号
     * @return
     */
    public FastormSqlBuilder<T> right() {
        FastormGroupJoinSqlExpression joinSqlExpression = groupJoinSqlExpressionQueue.poll();
        if (joinSqlExpression == null) {
            throw new FastormException("找不到对应的左括号，请检查代码调用顺序");
        }

        joinSqlExpression.join();

        FastormGroupJoinSqlExpression joinSqlExpressionParent = groupJoinSqlExpressionQueue.peek();
        if (joinSqlExpressionParent != null) {
            joinSqlExpressionConsumer = joinSqlExpressionParent.getExpressions()::add;
        } else {
            joinSqlExpressionConsumer = joinSqlExpression.getConsumer();
        }

        return this;
    }

    /**
     * and
     * @return
     */
    public FastormSqlBuilder<T> and() {
        joinType = SqlJoinType.AND;

        return this;
    }

    /**
     * and
     * @return
     */
    public FastormSqlBuilder<T> or() {
        joinType = SqlJoinType.OR;

        return this;
    }

    /**
     * where 1=1
     * @return
     */
    public FastormSqlBuilder<T> oneEqOne() {
        return eq(new NumberSqlField(1), new NumberSqlField(1));
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, Integer> func, Integer val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, Long> func, Long val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, Float> func, Float val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, Double> func, Double val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, Boolean> func, Boolean val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, String> func, String val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, BigDecimal> func, BigDecimal val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, Date> func, Date val) {
        return eq(func, (Object) val);
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    private FastormSqlBuilder<T> eq(Function<T, ?> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        return eq(new NameSqlField(columnName), new VariableSqlField(clazz, val));
    }

    /**
     * where #{func} = #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return eq(field, val);
    }

    /**
     * where #{field} = #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> eq(SqlField field, SqlField val) {
        SqlExpression expression = new EqualSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, Integer> func, Integer val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, Long> func, Long val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, Float> func, Float val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, Double> func, Double val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, Boolean> func, Boolean val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, String> func, String val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, BigDecimal> func, BigDecimal val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(Function<T, Date> func, Date val) {
        return notEq(func, (Object) val);
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @return
     */
    private FastormSqlBuilder<T> notEq(Function<T, ?> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        return notEq(new NameSqlField(columnName), new VariableSqlField(clazz, val));
    }

    /**
     * where #{func} != #{val}
     * @param func
     * @param val
     * @param <F>
     * @return
     */
    public <F> FastormSqlBuilder<T> notEq(Function<T, F> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return notEq(field, val);
    }

    /**
     * where #{field} != #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> notEq(SqlField field, SqlField val) {
        SqlExpression expression = new NotEqualSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, Integer> func, Integer val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, Long> func, Long val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, Float> func, Float val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, Double> func, Double val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, Boolean> func, Boolean val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, BigDecimal> func, BigDecimal val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, String> func, String val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, Date> func, Date val) {
        return lt(func, (Object) val);
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @param <F>
     * @return
     */
    private <F> FastormSqlBuilder<T> lt(Function<T, F> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        return lt(new NameSqlField(columnName), new VariableSqlField(clazz, val));
    }

    /**
     * where #{func} < #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return lt(field, val);
    }

    /**
     * where #{field} < #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> lt(SqlField field, SqlField val) {
        SqlExpression expression = new LessThanSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, Integer> func, Integer val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, Long> func, Long val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, Float> func, Float val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, Double> func, Double val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, Boolean> func, Boolean val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, BigDecimal> func, BigDecimal val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, String> func, String val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, Date> func, Date val) {
        return ltEq(func, (Object) val);
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    private FastormSqlBuilder<T> ltEq(Function<T, ?> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        return ltEq(new NameSqlField(columnName), new VariableSqlField(clazz, val));
    }

    /**
     * where #{func} <= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return ltEq(field, val);
    }

    /**
     * where #{field} <= #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> ltEq(SqlField field, SqlField val) {
        SqlExpression expression = new LessThanAndEqualSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, Integer> func, Integer val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, Long> func, Long val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, Float> func, Float val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, Double> func, Double val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, Boolean> func, Boolean val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, BigDecimal> func, BigDecimal val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, String> func, String val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, Date> func, Date val) {
        return gt(func, (Object) val);
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    private FastormSqlBuilder<T> gt(Function<T, ?> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        return gt(new NameSqlField(fieldName), new VariableSqlField(clazz, val));
    }

    /**
     * where #{func} > #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return gt(field, val);
    }

    /**
     * where #{func} > #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gt(SqlField field, SqlField val) {
        SqlExpression expression = new GreaterThanSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, Integer> func, Integer val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, Long> func, Long val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, Float> func, Float val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, Double> func, Double val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, Boolean> func, Boolean val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, BigDecimal> func, BigDecimal val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, String> func, String val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, Date> func, Date val) {
        return gtEq(func, (Object) val);
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    private FastormSqlBuilder<T> gtEq(Function<T, ?> func, Object val) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, Collections.singletonList(val));

        return gtEq(new NameSqlField(columnName), new VariableSqlField(clazz, val));
    }

    /**
     * where #{func} >= #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return gtEq(field, val);
    }

    /**
     * where #{field} >= #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> gtEq(SqlField field, SqlField val) {
        SqlExpression expression = new GreaterThanAndEqualSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, Integer... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, Long... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, Float... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, Double... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, Boolean... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, String... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, BigDecimal... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, Date... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return in(func, collection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, ObjCollection collection) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, collection);

        SqlFieldCollection sqlFieldList = new SqlFieldCollection(collection.size());
        for (Object obj : collection) {
            sqlFieldList.add(new VariableSqlField(clazz, obj));
        }

        return in(new NameSqlField(columnName), sqlFieldList);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, SqlField... collection) {
        String columnName = beanStructure.findColumnName(func);
        SqlFieldCollection fieldCollection = new SqlFieldCollection(collection.length);
        Collections.addAll(fieldCollection, collection);

        return in(new NameSqlField(columnName), fieldCollection);
    }

    /**
     * where #{func} in (...)
     * @param func
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> in(Function<T, ?> func, SqlFieldCollection collection) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return in(field, collection);
    }

    /**
     * where #{field} in (...)
     * @param field
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> in(SqlField field, SqlField... collection) {
        SqlExpression expression = new InSqlExpression(field, collection);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{field} in (...)
     * @param field
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> in(SqlField field, SqlFieldCollection collection) {
        return in(field, collection.toArray(new SqlField[0]));
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, Integer... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, Long... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, Float... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, Double... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, Boolean... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, String... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, BigDecimal... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param list
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, Date... list) {
        ObjCollection collection = new ObjCollection(list.length);
        Collections.addAll(collection, list);
        return notIn(func, collection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, ObjCollection collection) {
        String fieldName = beanStructure.findFieldName(func);
        String columnName = beanStructure.findColumnName(fieldName);
        Class<?> clazz = beanStructure.getReadMethodMap().get(fieldName).getReturnType();

        checkTypes(clazz, collection);

        SqlFieldCollection sqlFieldList = new SqlFieldCollection(collection.size());
        for (Object obj : collection) {
            sqlFieldList.add(new VariableSqlField(clazz, obj));
        }

        return notIn(new NameSqlField(columnName), sqlFieldList);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, SqlField... collection) {
        String columnName = beanStructure.findColumnName(func);
        SqlFieldCollection fieldCollection = new SqlFieldCollection(collection.length);
        Collections.addAll(fieldCollection, collection);

        return notIn(new NameSqlField(columnName), fieldCollection);
    }

    /**
     * where #{func} not in (...)
     * @param func
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> notIn(Function<T, ?> func, SqlFieldCollection collection) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return notIn(field, collection);
    }

    /**
     * where #{field} in (...)
     * @param field
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> notIn(SqlField field, SqlField... collection) {
        SqlExpression expression = new NotInSqlExpression(field, collection);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * where #{field} in (...)
     * @param field
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> notIn(SqlField field, SqlFieldCollection collection) {
        return notIn(field, collection.toArray(new SqlField[0]));
    }

    /**
     * where #{func} like concat('%', #{val}, '%')
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> like(Function<T, String> func, String val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);
        SqlField v = new VariableSqlField(String.class, val);

        return like(field, new ConcatMethodSqlField(v, 1, new CharSqlField("%"), new CharSqlField("%")));
    }

    /**
     * where #{func} like concat(#{val}, '%')
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> likeStart(Function<T, String> func, String val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);
        SqlField v = new VariableSqlField(String.class, val);

        return like(field, new ConcatMethodSqlField(v, 0, new CharSqlField("%")));
    }

    /**
     * where #{func} like concat('%', #{val})
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> likeEnd(Function<T, String> func, String val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);
        SqlField v = new VariableSqlField(String.class, val);

        return like(field, new ConcatMethodSqlField(v, 1, new CharSqlField("%")));
    }

    /**
     * where #{func} like #{val}
     * @param func
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> like(Function<T, ?> func, SqlField val) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return like(field, val);
    }

    /**
     * where #{field} like #{val}
     * @param field
     * @param val
     * @return
     */
    public FastormSqlBuilder<T> like(SqlField field, SqlField val) {
        SqlExpression expression = new LikeSqlExpression(field, val);
        SqlExpression expression2 = new JoinSqlExpression(joinType, expression);
        joinSqlExpressionConsumer.accept(expression2);

        return this;
    }

    /**
     * group by #{field}...
     * @param fields
     * @return
     */
    public FastormSqlBuilder<T> groupBy(Function<T, ?>... fields) {
        FuncCollection<T> collection = new FuncCollection<>(fields.length);
        Collections.addAll(collection, fields);
        return groupBy(collection);
    }

    /**
     * group by #{field}...
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> groupBy(FuncCollection<T> collection) {
        SqlFieldCollection fieldCollection = new SqlFieldCollection(collection.size());
        for (Function<T, ?> function : collection) {
            String columnName = beanStructure.findColumnName(function);
            fieldCollection.add(new NameSqlField(columnName));
        }

        return groupBy(fieldCollection);
    }

    /**
     * group by #{field}...
     * @param fields
     * @return
     */
    public FastormSqlBuilder<T> groupBy(SqlField... fields) {
        for (SqlField sqlField : fields) {
            sql.addGroupField(sqlField);
        }

        return this;
    }

    /**
     * group by #{field}...
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> groupBy(SqlFieldCollection collection) {
        return groupBy(collection.toArray(new SqlField[0]));
    }

    /**
     * order by #{field}...
     * @param func
     * @return
     */
    public FastormSqlBuilder<T> orderBy(Function<T, ?> func) {
        return orderBy(func, SqlOrderType.ASC);
    }

    /**
     * order by #{field}...
     * @param func
     * @param type
     * @return
     */
    public FastormSqlBuilder<T> orderBy(Function<T, ?> func, SqlOrderType type) {
        String columnName = beanStructure.findColumnName(func);
        SqlField field = new NameSqlField(columnName);

        return orderBy(new StandardSqlSqlOrder(field, type));
    }

    /**
     * order by #{field}...
     * @param orders
     * @return
     */
    public FastormSqlBuilder<T> orderBy(SqlOrder... orders) {
        SqlOrderCollection collection = new SqlOrderCollection(orders.length);
        Collections.addAll(collection, orders);

        return orderBy(collection);
    }

    /**
     * order by #{field}...
     * @param collection
     * @return
     */
    public FastormSqlBuilder<T> orderBy(SqlOrderCollection collection) {
        for (SqlOrder sqlOrder : collection) {
            sql.addOrderField(sqlOrder);
        }

        return this;
    }

    /**
     * limit (#{page} - 1) * #{size}, #{size}
     * @param page
     * @param length
     * @return
     */
    public FastormSqlBuilder<T> page(int page, int length) {
        return limit((page - 1) * length, length);
    }

    /**
     * limit #{index}
     * @param index
     * @return
     */
    public FastormSqlBuilder<T> limit(int index) {
        return limit(index, 0);
    }

    /**
     * limit #{index}, #{size}
     * @param index
     * @param length
     * @return
     */
    public FastormSqlBuilder<T> limit(int index, int length) {
        sql.setIndex(index);
        sql.setLength(length);

        return this;
    }

    /**
     * 检查数据类型是否匹配
     * @param type
     * @param list
     * @return
     */
    private void checkTypes(Class<?> type, List<Object> list) {
        for (Object obj : list) {
            if (!type.equals(obj.getClass())) {
                throw new FastormSqlException(String.format("参数类型不一致，%s != %s", type.getName(), obj.getClass().getName()));
            }
        }
    }

    @Override
    public int exec() {
        return sqlExecutor.exec();
    }

    @Override
    public int exec(FastormGeneratedKey<T> generatedKey) {
        return sqlExecutor.exec(generatedKey);
    }

    @Override
    public T read() {
        return sqlExecutor.read();
    }

    @Override
    public List<T> readList() {
        return sqlExecutor.readList();
    }

    @Override
    public <F> F read(Class<F> clazz) {
        return sqlExecutor.read(clazz);
    }

    @Override
    public <F> List<F> readList(Class<F> clazz) {
        return sqlExecutor.readList(clazz);
    }
}
