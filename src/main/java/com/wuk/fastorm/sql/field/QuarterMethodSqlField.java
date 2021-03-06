package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * quarter(field)
 */
public class QuarterMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public QuarterMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public QuarterMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public QuarterMethodSqlField(SqlField field) {
        this(field, null);
    }

    public QuarterMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("QUARTER(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
