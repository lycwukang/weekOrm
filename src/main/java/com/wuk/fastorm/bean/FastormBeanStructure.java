package com.wuk.fastorm.bean;

import com.wuk.fastorm.annontation.FastormColumn;
import com.wuk.fastorm.annontation.FastormTable;
import com.wuk.fastorm.proxy.DefaultLastOperateFeatureFactory;
import com.wuk.fastorm.proxy.LastOperateFeature;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FastormBeanStructure<T> extends BeanStructure<T> {

    private FastormTable table;
    private Map<String, FastormColumn> columnMap;

    public FastormBeanStructure(BeanStructure<T> beanStructure) {
        setClazz(beanStructure.getClazz());
        setFieldNames(beanStructure.getFieldNames());
        setFieldMap(beanStructure.getFieldMap());
        setReadMethodMap(beanStructure.getReadMethodMap());
        setWriteMethodMap(beanStructure.getWriteMethodMap());
    }

    public FastormTable getTable() {
        return table;
    }

    public void setTable(FastormTable table) {
        this.table = table;
    }

    public Map<String, FastormColumn> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, FastormColumn> columnMap) {
        this.columnMap = columnMap;
    }

    /**
     * 获取列名称
     * @param fieldName
     * @return
     */
    public String findColumnName(String fieldName) {
        return columnMap.get(fieldName).value();
    }

    /**
     * 列是否是autoIncrement
     * @param fieldName
     * @return
     */
    public boolean isAutoIncrement(String fieldName) {
        return columnMap.get(fieldName).autoIncrement();
    }

    /**
     * 读取数据库结果
     * @param resultSet
     * @return
     */
    public List<T> readResultSet(ResultSet resultSet) throws SQLException {
        Map<String, String> columnMapping = new HashMap<>();
        for (String fieldName : getFieldNames()) {
            columnMapping.put(columnMap.get(fieldName).value(), fieldName);
        }

        return super.readResultSet(resultSet, columnMapping);
    }
}
