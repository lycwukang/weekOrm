package wuk.week.orm;

import java.lang.reflect.Field;

public class ColumnHelper {

    public static Column count() {
        return Column.COUNT_FIELD;
    }

    public static Column field(Field field) {
        return new Column(field);
    }

    public static Column field(Field field, MethodType method) {
        return new Column(field, method);
    }

    public static void builder(StringBuilder builder, BeanDeclare beanDeclare, Column column) {
        Field field = column.getField();
        MethodType method = column.getMethod();

        if (method.equals(MethodType.normal)) {
            builder.append("`").append(beanDeclare.getColumnName(field)).append("`");
        } else if (method.equals(MethodType.count)) {
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
    }
}