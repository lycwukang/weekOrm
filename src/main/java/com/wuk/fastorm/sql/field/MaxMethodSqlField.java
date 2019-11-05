package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * max(field)
 */
public class MaxMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public MaxMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public MaxMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public MaxMethodSqlField(SqlField field) {
        this(field, null);
    }

    public MaxMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("MAX(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
