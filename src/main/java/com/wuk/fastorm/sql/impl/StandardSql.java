package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * sql语句的抽象实例
 */
public class StandardSql implements Sql {

    /**
     * schema名，如果为空，则不显示
     */
    private String schema;

    /**
     * 表名
     */
    private String table;

    /**
     * sql类型
     */
    private SqlType type;

    /**
     * on duplicate key update
     */
    private boolean onDuplicateKeyUpdate;

    /**
     * insert into (var0, var1, var2...)
     */
    private List<SqlField> insertFieldList = new ArrayList<>();

    /**
     * values(var0, var1, var2...),(var0, var1, var2...),(var0, var1, var2...)...
     */
    private List<List<SqlParam>> insertDataList = new ArrayList<>();

    /**
     * select field0, file1, file2...
     */
    private List<SqlField> fieldList = new ArrayList<>();

    /**
     * set val0, val1, val2...
     */
    private List<SqlSet> setList = new ArrayList<>();

    /**
     * where val0, val1, val2...
     */
    private List<SqlExpression> whereList = new ArrayList<>();

    /**
     * group by field0, field1, field2...
     */
    private List<SqlField> groupFieldList = new ArrayList<>();

    /**
     * having val0, val1, val2...
     */
    private List<SqlExpression> havingList = new ArrayList<>();

    /**
     * order by field0, field1, field2...
     */
    private List<SqlOrder> orderFieldList = new ArrayList<>();

    /**
     * limit index, y
     */
    private int index;

    /**
     * limit x, length
     */
    private int length;

    /**
     * sql语句生成器
     */
    private StandardSqlSqlTextBuilder selectSqlBuilder = new StandardSqlSqlTextSelectBuilder();
    private StandardSqlSqlTextBuilder updateSqlBuilder = new StandardSqlSqlTextUpdateBuilder();
    private StandardSqlSqlTextBuilder insertSqlBuilder = new StandardSqlSqlTextInsertBuilder();
    private StandardSqlSqlTextBuilder deleteSqlBuilder = new StandardSqlSqlTextDeleteBuilder();

    @Override
    public String getSql() {
        switch (type) {
            case INSERT:
                return insertSqlBuilder.toSql(this);
            case UPDATE:
                return updateSqlBuilder.toSql(this);
            case DELETE:
                return deleteSqlBuilder.toSql(this);
            case SELECT:
                return selectSqlBuilder.toSql(this);
            default:
                return "";
        }
    }

    @Override
    public List<SqlParam> getParams() {
        switch (type) {
            case INSERT:
                return insertSqlBuilder.findParams(this);
            case UPDATE:
                return updateSqlBuilder.findParams(this);
            case DELETE:
                return deleteSqlBuilder.findParams(this);
            case SELECT:
                return selectSqlBuilder.findParams(this);
            default:
                return Collections.emptyList();
        }
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setType(SqlType type) {
        this.type = type;
    }

    public void setOnDuplicateKeyUpdate(boolean onDuplicateKeyUpdate) {
        this.onDuplicateKeyUpdate = onDuplicateKeyUpdate;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void addInsertFieldList(SqlField field) {
        insertFieldList.add(field);
    }

    public void addInsertDataList(List<SqlParam> params) {
        insertDataList.add(params);
    }

    public void addField(SqlField field) {
        fieldList.add(field);
    }

    public void addSet(SqlSet set) {
        setList.add(set);
    }

    public void addWhere(SqlExpression expression) {
        whereList.add(expression);
    }

    public void addGroupField(SqlField field) {
        groupFieldList.add(field);
    }

    public void addHaving(SqlExpression expression) {
        havingList.add(expression);
    }

    public void addOrderField(SqlOrder order) {
        orderFieldList.add(order);
    }

    public String getSchema() {
        return schema;
    }

    public String getTable() {
        return table;
    }

    public SqlType getType() {
        return type;
    }

    public boolean isOnDuplicateKeyUpdate() {
        return onDuplicateKeyUpdate;
    }

    public List<SqlField> getInsertFieldList() {
        return insertFieldList;
    }

    public List<List<SqlParam>> getInsertDataList() {
        return insertDataList;
    }

    public List<SqlField> getFieldList() {
        return fieldList;
    }

    public List<SqlSet> getSetList() {
        return setList;
    }

    public List<SqlExpression> getWhereList() {
        return whereList;
    }

    public List<SqlField> getGroupFieldList() {
        return groupFieldList;
    }

    public List<SqlExpression> getHavingList() {
        return havingList;
    }

    public List<SqlOrder> getOrderFieldList() {
        return orderFieldList;
    }

    public int getIndex() {
        return index;
    }

    public int getLength() {
        return length;
    }
}
