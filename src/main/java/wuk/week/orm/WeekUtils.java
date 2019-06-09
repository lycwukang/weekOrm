package wuk.week.orm;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeekUtils {

    // 可以进行反射操作的属性类型
    private static final Class<?>[] PrimitiveTypes = {
            int.class, Integer.class, long.class, Long.class,
            float.class, Float.class, double.class, Double.class,
            boolean.class, Boolean.class, String.class, BigDecimal.class, Date.class
    };
    // 类型转换表
    private static final Map<Class<?>, Class<?>> PrimitiveConvertTable = new HashMap<>();

    static {
        PrimitiveConvertTable.put(int.class, Integer.class);
        PrimitiveConvertTable.put(double.class, Double.class);
        PrimitiveConvertTable.put(float.class, Float.class);
        PrimitiveConvertTable.put(long.class, Long.class);
        PrimitiveConvertTable.put(boolean.class, Boolean.class);
    }

    public static boolean isPrimitive(Class<?> clazz) {
        return ArrayUtils.contains(PrimitiveTypes, clazz);
    }

    public static boolean equalsPrimitive(Class<?> clazz, Class<?> clazz0) {
        return PrimitiveConvertTable.getOrDefault(clazz, clazz).equals(PrimitiveConvertTable.getOrDefault(clazz0, clazz0));
    }

    public static Field field(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Throwable t) {
            throw new RuntimeException("查询不到字段信息[class=" + clazz.getName() + ", name=" + name + "]");
        }
    }
}