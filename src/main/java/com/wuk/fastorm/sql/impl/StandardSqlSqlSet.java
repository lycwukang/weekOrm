package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlSet;

import java.util.List;

public class StandardSqlSqlSet implements SqlSet {

    private String name;
    private SqlField field;

    public StandardSqlSqlSet(String name, SqlField field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public String getSql() {
        return String.format("%s = %s", name, field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return field.getParams();
    }
}
