package com.wuk.fastorm.bean;

import com.wuk.fastorm.exception.FastormBeanBindException;
import com.wuk.fastorm.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * 通过{@link com.wuk.fastorm.bean.BeanStructure}解析出所有字段信息
 */
public class BeanStructure<T> {

    private Class<T> clazz;
    private List<String> fieldNames;
    private Map<String, Field> fieldMap;
    private Map<String, Method> readMethodMap;
    private Map<String, Method> writeMethodMap;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Field> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, Method> getReadMethodMap() {
        return readMethodMap;
    }

    public void setReadMethodMap(Map<String, Method> readMethodMap) {
        this.readMethodMap = readMethodMap;
    }

    public Map<String, Method> getWriteMethodMap() {
        return writeMethodMap;
    }

    public void setWriteMethodMap(Map<String, Method> writeMethodMap) {
        this.writeMethodMap = writeMethodMap;
    }

    /**
     * 读取数据库结果
     * @param resultSet
     * @return
     */
    public List<T> readResultSet(ResultSet resultSet) throws SQLException {
        Map<String, String> columnMapping = new HashMap<>();
        for (String fieldName : fieldNames) {
            columnMapping.put(fieldName, fieldName);

            StringBuilder builder = new StringBuilder();
            for (char c : fieldName.toCharArray()) {
                if (c >= 'A' && c <= 'Z') {
                    builder.append("_").append(Character.toLowerCase(c));
                } else {
                    builder.append(c);
                }
            }

            if (!builder.toString().equals(fieldName)) {
                columnMapping.put(builder.toString(), fieldName);
            }
        }

        return readResultSet(resultSet, columnMapping);
    }

    /**
     * 读取数据库结果
     * @param resultSet
     * @param columnMapping
     * @return
     * @throws SQLException
     */
    public List<T> readResultSet(ResultSet resultSet, Map<String, String> columnMapping) throws SQLException {
        List<T> beanList = new ArrayList<>();
        while (resultSet.next()) {
            T t;
            try {
                t = getClazz().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new FastormBeanBindException(getClazz(), "数据绑定出错", e);
            }

            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                int index = i + 1;
                String columnName = metaData.getColumnLabel(index);
                String fieldName = columnMapping.get(columnName);
                if (fieldName != null) {
                    Method writeMethod = getWriteMethodMap().get(fieldName);
                    try {
                        writeMethod.invoke(t, findValue(resultSet, index, getReadMethodMap().get(fieldName).getReturnType()));
                    } catch (Exception e) {
                        throw new FastormBeanBindException(getClazz(), "数据绑定出错", e);
                    }
                }
            }

            beanList.add(t);
        }
        return beanList;
    }

    private Object findValue(ResultSet resultSet, int index, Class<?> clazz) throws SQLException {
        if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return findInt(resultSet, index);
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return findLong(resultSet, index);
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return findFloat(resultSet, index);
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return findDouble(resultSet, index);
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return findBoolean(resultSet, index);
        } else if (clazz.equals(String.class)) {
            return findString(resultSet, index);
        } else if (clazz.equals(BigDecimal.class)) {
            return findBigDecimal(resultSet, index);
        } else if (clazz.equals(Date.class)) {
            return findDate(resultSet, index);
        }

        return null;
    }

    private Integer findInt(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getInt(index);
    }

    private Long findLong(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getLong(index);
    }

    private Float findFloat(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getFloat(index);
    }

    private Double findDouble(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getDouble(index);
    }

    private Boolean findBoolean(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getBoolean(index);
    }

    private String findString(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getString(index);
    }

    private BigDecimal findBigDecimal(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }
        return resultSet.getBigDecimal(index);
    }

    private Date findDate(ResultSet resultSet, int index) throws SQLException {
        Object obj = resultSet.getObject(index);
        if (obj == null) {
            return null;
        }

        if (obj instanceof Timestamp) {
            return new Date(resultSet.getTimestamp(index).getTime());
        }
        return resultSet.getDate(index);
    }

    /**
     * 读取数据库结果
     * @param resultSet
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <F> List<F> readResultSet(ResultSet resultSet, Class<F> clazz) throws SQLException {
        if (ArrayUtils.contains(DefaultBeanAnalyze.ANALYZE_ENABLED_TYPES, clazz)) {
            List<F> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add((F) resultSet.getObject(1));
            }
            return resultList;
        }
        return new DefaultBeanAnalyze().analyze(clazz).readResultSet(resultSet);
    }
}
