package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * reverse(field)
 */
public class ReverseMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public ReverseMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public ReverseMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public ReverseMethodSqlField(SqlField field) {
        this(field, null);
    }

    public ReverseMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("REVERSE(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
