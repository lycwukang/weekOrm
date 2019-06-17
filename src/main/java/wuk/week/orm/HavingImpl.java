package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HavingImpl implements Where {

    private Column column;
    private Object value;
    private OperatorType operatorType;
    private JoinType joinType;
    private Where joinWhere;

    public HavingImpl(Column column, OperatorType operatorType, Object value) {
        this.column = column;
        this.operatorType = operatorType;
        this.value = value;
    }

    @Override
    public void builder(StringBuilder builder) {
        Field field = column.getField();
        MethodType method = column.getMethod();
        BeanDeclare beanDeclare = BeanDeclare.findDeclare(field.getDeclaringClass());

        if (method.equals(MethodType.count)) {
            builder.append("count(1)");
        } else if (method.equals(MethodType.sum)) {
            builder.append("sum(`").append(beanDeclare.getColumnName(field)).append("`)");
        } else if (method.equals(MethodType.avg)) {
            builder.append("avg(`").append(beanDeclare.getColumnName(field)).append("`)");
        } else if (method.equals(MethodType.max)) {
            builder.append("max(`").append(beanDeclare.getColumnName(field)).append("`)");
        } else if (method.equals(MethodType.min)) {
            builder.append("min(`").append(beanDeclare.getColumnName(field)).append("`)");
        }
        builder.append(" ").append(OperatorType.sql(operatorType)).append(" ?");

        if (joinWhere != null) {
            builder.append(" ").append(JoinType.sql(joinType)).append(" ");
            joinWhere.builder(builder);
        }
    }

    @Override
    public List<Pair<Field, Object>> builderObj() {
        List<Pair<Field, Object>> list = new ArrayList<>();
        if (!column.getMethod().equals(MethodType.count)) {
            list.add(new Pair<>(column.getField(), value));
        }
        return list;
    }

    @Override
    public Where and(Where where) {
        joinType = JoinType.and;
        return joinWhere = where;
    }

    @Override
    public Where or(Where where) {
        joinType = JoinType.or;
        return joinWhere = where;
    }
}