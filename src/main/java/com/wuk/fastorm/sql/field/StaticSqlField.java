package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * 列名
 */
public class StaticSqlField extends AbstractSqlField {

    private String name;

    public StaticSqlField(String name) {
        this(name, null);
    }

    public StaticSqlField(String name, String aliasName) {
        super(aliasName);
        this.name = name;
    }

    @Override
    public String toSql() {
        return name;
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
