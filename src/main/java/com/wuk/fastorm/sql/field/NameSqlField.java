package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * 列名
 */
public class NameSqlField extends AbstractSqlField {

    private String name;

    public NameSqlField(String name) {
        this(name, null);
    }

    public NameSqlField(String name, String aliasName) {
        super(aliasName);
        this.name = name;
    }

    @Override
    public String toSql() {
        return String.format("`%s`", name);
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
