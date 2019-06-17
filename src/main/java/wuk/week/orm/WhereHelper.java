package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhereHelper {

    public static Where eq(Field field, Object value) {
        return new WhereImpl(field, OperatorType.eq, Collections.singletonList(value));
    }

    public static Where notEq(Field field, Object value) {
        return new WhereImpl(field, OperatorType.notEq, Collections.singletonList(value));
    }

    public static Where lt(Field field, Object value) {
        return new WhereImpl(field, OperatorType.lt, Collections.singletonList(value));
    }

    public static Where ltEq(Field field, Object value) {
        return new WhereImpl(field, OperatorType.ltEq, Collections.singletonList(value));
    }

    public static Where gt(Field field, Object value) {
        return new WhereImpl(field, OperatorType.gt, Collections.singletonList(value));
    }

    public static Where gtEq(Field field, Object value) {
        return new WhereImpl(field, OperatorType.gtEq, Collections.singletonList(value));
    }

    public static Where in(Field field, Object... values) {
        List<Object> objects = new ArrayList<>();
        Collections.addAll(objects, values);
        return in(field, objects);
    }

    public static Where in(Field field, List<Object> values) {
        return new WhereImpl(field, OperatorType.in, values);
    }

    public static Where notIn(Field field, Object[] values) {
        List<Object> objects = new ArrayList<>();
        Collections.addAll(objects, values);
        return notIn(field, objects);
    }

    public static Where notIn(Field field, List<Object> values) {
        return new WhereImpl(field, OperatorType.notIn, values);
    }

    public static Where like(Field field, String value) {
        return new WhereImpl(field, OperatorType.like, Collections.singletonList(value));
    }

    public static Where group(Where where) {
        return new WhereGroup(where);
    }
}