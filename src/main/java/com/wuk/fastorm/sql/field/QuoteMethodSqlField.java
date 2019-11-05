package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * quote(field)
 */
public class QuoteMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public QuoteMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public QuoteMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public QuoteMethodSqlField(SqlField field) {
        this(field, null);
    }

    public QuoteMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("QUOTE(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
