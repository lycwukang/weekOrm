package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * ascii(field)
 */
public class AsciiMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public AsciiMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public AsciiMethodSqlField(SqlField field) {
        this(field, null);
    }

    public AsciiMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public AsciiMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("ASCII(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
