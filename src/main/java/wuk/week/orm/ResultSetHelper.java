package wuk.week.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultSetHelper {

    /**
     * 读取第一行数据
     * @param set
     * @return
     * @throws SQLException
     */
    public static <F> F read(ResultSet set, Class<F> fClazz) throws SQLException {
        if (set.next()) {
            return resultOne(set, fClazz);
        }
        return null;
    }

    /**
     * 读取所有数据
     * @param set
     * @return
     * @throws SQLException
     */
    public static <F> List<F> readList(ResultSet set, Class<F> fClazz) throws SQLException {
        List<F> list = new ArrayList<>();
        while (set.next()) {
            list.add(resultOne(set, fClazz));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private static <F> F resultOne(ResultSet set, Class<F> clazz) throws SQLException {
        if (WeekUtils.isPrimitive(clazz)) {
            return (F) set.getObject(1);
        } else {
            BeanDeclare<F> beanDeclare = BeanDeclare.findDeclare(clazz);
            F data = newInstance(clazz);
            ResultSetMetaData metaData = set.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Field field = beanDeclare.getFieldByColumnName(columnName);
                int index = set.findColumn(columnName);
                Object value = set.getObject(index);
                if (value != null) {
                    if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                        value = set.getInt(index);
                    } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                        value = set.getLong(index);
                    } else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
                        value = set.getFloat(index);
                    } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                        value = set.getDouble(index);
                    } else if (field.getType().equals(String.class)) {
                        value = set.getString(index);
                    } else if (field.getType().equals(Boolean.class)) {
                        value = set.getBoolean(index);
                    } else if (field.getType().equals(Date.class)) {
                        if (value.getClass().equals(Timestamp.class)) {
                            value = new Date(set.getTimestamp(index).getTime());
                        } else {
                            value = set.getDate(index);
                        }
                    } else if (field.getType().equals(BigDecimal.class)) {
                        value = set.getBigDecimal(index);
                    }

                    beanDeclare.setFieldValue(field, data, value);
                }
            }
            return data;
        }
    }

    private static <F> F newInstance(Class<F> clazz) {
        F data;
        try {
            data = clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("class没有默认的构造函数，无法创建bean实体[class=" + clazz.getName());
        } catch (InstantiationException e) {
            throw new RuntimeException("class是抽象类或接口，无法创建bean实体[class=" + clazz.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("class的构造函数不可访问，无法创建bean实体[class=" + clazz.getName());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("class传入给构造函数的参数不合法，无法创建bean实体[class=" + clazz.getName());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("class的构造函数内部出现错误，无法创建bean实体[class=" + clazz.getName());
        }
        return data;
    }
}