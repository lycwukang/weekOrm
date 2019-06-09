package wuk.week.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象声明，解析对象的各种属性
 * @param <T>
 */
public class BeanDeclare<T> {

    private static final Logger logger = LoggerFactory.getLogger(BeanDeclare.class);

    // === 静态变量
    // 存储解析过的对象
    private static Map<Class<?>, BeanDeclare<?>> beanDeclareMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> BeanDeclare<T> findDeclare(Class<T> clazz) {
        BeanDeclare declare = beanDeclareMap.get(clazz);
        if (declare == null) {
            beanDeclareMap.putIfAbsent(clazz, new BeanDeclare<>(clazz));
            declare = beanDeclareMap.get(clazz);
        }
        return declare;
    }

    // class类型
    private Class<T> clazz;
    // 是否是orm类型
    private boolean isOrm;
    // bean中包含的字段
    private List<Field> fields = new ArrayList<>();
    // bean中需要设置内容的属性
    private Map<Field, Method> settingMethods = new HashMap<>();
    // bean中可以获取内容的属性
    private Map<Field, Method> gettingMethods = new HashMap<>();

    public BeanDeclare(Class<T> clazz) {
        this.clazz = clazz;

        if (WeekUtils.isPrimitive(clazz)) {
            throw new RuntimeException("bean是基本类型，无法创建BeanDeclare[class=" + clazz.getName() + "]");
        }

        WeekTable tableAnnotation = clazz.getAnnotation(WeekTable.class);
        if (tableAnnotation != null) {
            if ("".equals(tableAnnotation.value())) {
                throw new RuntimeException("bean的OrmTable特性中不包含value值[class=" + clazz.getName() + "]");
            }
            isOrm = true;
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                if (isOrm) {
                    WeekColumn fieldAnnotation = field.getAnnotation(WeekColumn.class);
                    if (fieldAnnotation == null) {
                        throw new RuntimeException("bean的字段中不包含OrmColumn特性[class=" + clazz.getName() + ", name=" + field.getName() + "]");
                    }
                }

                if (!WeekUtils.isPrimitive(field.getType())) {
                    throw new RuntimeException("bean的字段不是基本数据类型[class=" + clazz.getName() + ", name=" + field.getName() + "]");
                }

                fields.add(field);

                Method settingMethod = getFieldSettingMethod(field);
                if (settingMethod == null) {
                    throw new RuntimeException("bean的字段不包含setting方法[class=" + clazz.getName() + ", name=" + field.getName() + "]");
                }

                Method gettingMethod = getFieldGettingMethod(field);
                if (gettingMethod == null || !WeekUtils.equalsPrimitive(gettingMethod.getReturnType(), field.getType())) {
                    throw new RuntimeException("bean的字段不包含getting方法[class=" + clazz.getName() + ", name=" + field.getName() + "]");
                }

                settingMethods.put(field, settingMethod);
                gettingMethods.put(field, gettingMethod);
            }
        }
    }

    private Method getFieldSettingMethod(Field field) {
        Method settingMethod = null;
        List<String> setMethodNames = getFieldSettingMethodName(field.getName(), field.getType());
        for (String methodName : setMethodNames) {
            try {
                settingMethod = clazz.getMethod(methodName, field.getType());
            } catch (NoSuchMethodException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[可忽略]bean中查询不到方法[class=" + field.getDeclaringClass() + ", methodName=" + methodName + "]");
                }
            }

            if (settingMethod != null) {
                if (Modifier.isPublic(settingMethod.getModifiers()) && !Modifier.isStatic(settingMethod.getModifiers()) && !Modifier.isFinal(settingMethod.getModifiers())) {
                    break;
                }
            }
        }

        return settingMethod;
    }

    private Method getFieldGettingMethod(Field field) {
        Method gettingMethod = null;
        List<String> getMethodNames = getFieldGettingMethodName(field.getName(), field.getType());
        for (String methodName : getMethodNames) {
            try {
                gettingMethod = clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("[可忽略]bean中查询不到方法[class=" + field.getDeclaringClass() + ", methodName=" + methodName + "]");
                }
            }

            if (gettingMethod != null) {
                if (Modifier.isPublic(gettingMethod.getModifiers()) && !Modifier.isStatic(gettingMethod.getModifiers()) && !Modifier.isFinal(gettingMethod.getModifiers())) {
                    break;
                }
            }
        }

        return gettingMethod;
    }

    private List<String> getFieldSettingMethodName(String fileName, Class<?> clazz) {
        List<String> methodNames = new ArrayList<>();
        if (fileName.startsWith("is") && WeekUtils.equalsPrimitive(clazz, Boolean.class)) {
            methodNames.add("set" + fileName.substring(2).substring(0, 1).toUpperCase() + fileName.substring(3));
        }
        methodNames.add("set" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
        return methodNames;
    }

    private List<String> getFieldGettingMethodName(String fileName, Class<?> clazz) {
        List<String> methodNames = new ArrayList<>();
        if (fileName.startsWith("is") && WeekUtils.equalsPrimitive(clazz, Boolean.class)) {
            methodNames.add("get" + fileName.substring(2).substring(0, 1).toUpperCase() + fileName.substring(3));
            methodNames.add("is" + fileName.substring(2).substring(0, 1).toUpperCase() + fileName.substring(3));
        }
        methodNames.add("get" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
        return methodNames;
    }

    /**
     * 检查Orm对象的字段合法性
     * @param field
     */
    private void checkOrmFieldLegal(Field field) {
        if (!isOrm) {
            throw new RuntimeException("bean不是Orm类型[class=" + clazz.getName() + "]");
        } else if (!isField(field)) {
            throw new RuntimeException("字段不属于该对象[class=" + clazz.getName() + ", fieldClass=" + field.getDeclaringClass().getName() + "]");
        }
    }

    /**
     * 解析的对象类型
     * @return
     */
    public Class<T> getClazz() {
        return clazz;
    }

    /**
     * 返回表数据
     * @return
     */
    public String getTableName() {
        return clazz.getAnnotation(WeekTable.class).value();
    }

    /**
     * 返回对象的所有字段
     * @return
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * 字段是否属于对象中
     * @param field
     * @return
     */
    public boolean isField(Field field) {
        return fields.contains(field);
    }

    /**
     * 字段是否是自增长键
     * @param field
     * @return
     */
    public boolean isAutoIncrement(Field field) {
        checkOrmFieldLegal(field);
        return field.getAnnotation(WeekColumn.class).autoIncrement();
    }

    /**
     * 字段是否是主键
     * @param field
     * @return
     */
    public boolean isPrimaryKey(Field field) {
        checkOrmFieldLegal(field);
        return field.getAnnotation(WeekColumn.class).primaryKey();
    }

    /**
     * 字段是否不为空
     * @param field
     * @return
     */
    public boolean isNotNull(Field field) {
        checkOrmFieldLegal(field);
        return field.getAnnotation(WeekColumn.class).notNull();
    }

    /**
     * 字段是否有默认值
     * @param field
     * @return
     */
    public boolean isDefaultValue(Field field) {
        checkOrmFieldLegal(field);
        return field.getAnnotation(WeekColumn.class).defaultValue();
    }

    /**
     * 字段对应的数据库列名
     * @param field
     * @return
     */
    public String getColumnName(Field field) {
        checkOrmFieldLegal(field);
        return field.getAnnotation(WeekColumn.class).value();
    }

    /**
     * 设置字段的值
     * @param field
     * @param obj
     * @param value
     */
    public void setFieldValue(Field field, T obj, Object value) {
        try {
            settingMethods.get(field).invoke(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("设置bean属性出错，方法可访问性不足[class=" + clazz.getName() + "，field=" + field.getName(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("设置bean属性出错，参数出现错误[class=" + clazz.getName() + "，field=" + field.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("设置bean属性出错，方法内部出现错误[class=" + clazz.getName() + "，field=" + field.getName(), e);
        }
    }

    /**
     * 获取字段的值
     * @param field
     * @param obj
     */
    public Object getFieldValue(Field field, T obj) {
        try {
            return gettingMethods.get(field).invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("查询bean属性出错，方法可访问性不足[class=" + clazz.getName() + "，field=" + field.getName(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("查询bean属性出错，参数出现错误[class=" + clazz.getName() + "，field=" + field.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("查询bean属性出错，方法内部出现错误[class=" + clazz.getName() + "，field=" + field.getName(), e);
        }
    }

    /**
     * 通过列名查询字段
     * @param name
     * @return
     */
    public Field getFieldByColumnName(String name) {
        for (Field field : fields) {
            if (isOrm) {
                if (getColumnName(field).equals(name)) {
                    return field;
                }
            } else if (field.getName().equals(name) || underlineName(field.getName()).equals(name)) {
                return field;
            }
        }
        throw new RuntimeException("无法查询到列名对应的字段信息[class=" + clazz.getName() + ", columnName=" + name + "]");
    }

    /**
     * 填充查询数据
     * @param statement
     * @param params
     * @throws SQLException
     */
    public void fillParams(PreparedStatement statement, List<Pair<Field, Object>> params) throws SQLException {
        for (int i = 1; i <= params.size(); i++) {
            Field field = params.get(i - 1).getT();
            Object obj = params.get(i - 1).getF();

            checkOrmFieldLegal(field);

            if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.INTEGER);
                } else {
                    statement.setInt(i, (int) obj);
                }
            } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.BIGINT);
                } else {
                    statement.setLong(i, (long) obj);
                }
            } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.FLOAT);
                } else {
                    statement.setFloat(i, (float) obj);
                }
            } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.DOUBLE);
                } else {
                    statement.setDouble(i, (double) obj);
                }
            } else if (field.getType().equals(String.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.VARCHAR);
                } else {
                    statement.setString(i, (String) obj);
                }
            } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.BOOLEAN);
                } else {
                    statement.setBoolean(i, (boolean) obj);
                }
            } else if (field.getType().equals(Date.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.DATE);
                } else {
                    statement.setDate(i, new java.sql.Date(((Date) obj).getTime()));
                }
            } else if (field.getType().equals(BigDecimal.class)) {
                if (obj == null) {
                    statement.setNull(i, Types.DECIMAL);
                } else {
                    statement.setBigDecimal(i, (BigDecimal) obj);
                }
            }
        }
    }

    private String underlineName(String name) {
        int index;
        while ((index = name.indexOf("_")) >= 0) {
            name = name.substring(0, index) + name.substring(index + 1, index + 2).toUpperCase() + name.substring(index + 2);
        }
        return name;
    }
}