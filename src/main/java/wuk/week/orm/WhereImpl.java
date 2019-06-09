package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class WhereImpl implements Where {

    private BeanDeclare beanDeclare;
    private Field field;
    private List<Object> values;
    private OperatorType operatorType;
    private JoinType joinType;
    private Where joinWhere;

    public WhereImpl(Field field, OperatorType operatorType, List<Object> values) {
        this.beanDeclare = BeanDeclare.findDeclare(field.getDeclaringClass());
        this.field = field;
        this.operatorType = operatorType;
        this.values = values;
    }

    @Override
    public void builder(StringBuilder builder) {
        builder.append("`").append(beanDeclare.getColumnName(field)).append("`");
        builder.append(" ").append(OperatorType.sql(operatorType)).append(" ");

        if (operatorType.equals(OperatorType.in) || operatorType.equals(OperatorType.notIn)) {
            builder.append("(");
            for (int i = 0; i < values.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("?");
            }
            builder.append(")");
        } else {
            builder.append("?");
        }

        if (joinWhere != null) {
            builder.append(" ").append(JoinType.sql(joinType)).append(" ");
            joinWhere.builder(builder);
        }
    }

    @Override
    public List<Pair<Field, Object>> builderObj() {
        List<Pair<Field, Object>> list = new ArrayList<>();
        for (Object value : values) {
            list.add(new Pair<>(field, value));
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