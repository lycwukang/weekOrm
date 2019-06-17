package wuk.week.orm;

import java.lang.reflect.Field;
import java.util.Collections;

public class HavingHelper {

    public static Where eq(Field field, MethodType methodType, Object value) {
        return new HavingImpl(ColumnHelper.field(field, methodType), OperatorType.eq, Collections.singletonList(value));
    }

    public static Where notEq(Field field, MethodType methodType, Object value) {
        return new HavingImpl(ColumnHelper.field(field, methodType), OperatorType.notEq, Collections.singletonList(value));
    }

    public static Where lt(Field field, MethodType methodType, Object value) {
        return new HavingImpl(ColumnHelper.field(field, methodType), OperatorType.lt, Collections.singletonList(value));
    }

    public static Where ltEq(Field field, MethodType methodType, Object value) {
        return new HavingImpl(ColumnHelper.field(field, methodType), OperatorType.ltEq, Collections.singletonList(value));
    }

    public static Where gt(Field field, MethodType methodType, Object value) {
        return new HavingImpl(ColumnHelper.field(field, methodType), OperatorType.gt, Collections.singletonList(value));
    }

    public static Where gtEq(Field field, MethodType methodType, Object value) {
        return new HavingImpl(ColumnHelper.field(field, methodType), OperatorType.gtEq, Collections.singletonList(value));
    }
}