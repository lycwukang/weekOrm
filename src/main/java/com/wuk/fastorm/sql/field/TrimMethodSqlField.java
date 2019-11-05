package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * trim(field)
 */
public class TrimMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public TrimMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public TrimMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public TrimMethodSqlField(SqlField field) {
        this(field, null);
    }

    public TrimMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("TRIM(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
