package wuk.week.orm;

import java.lang.reflect.Field;

public class Column {

    public static final Column COUNT_FIELD = new Column(null, MethodType.count);

    private Field field;
    private MethodType method;

    public Column(Field field) {
        this.field = field;
        this.method = MethodType.normal;
    }

    public Column(Field field, MethodType method) {
        this.field = field;
        this.method = method;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public MethodType getMethod() {
        return method;
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }
}