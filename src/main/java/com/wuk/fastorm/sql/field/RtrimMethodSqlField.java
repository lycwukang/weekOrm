package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * rtrim(field)
 */
public class RtrimMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public RtrimMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public RtrimMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public RtrimMethodSqlField(SqlField field) {
        this(field, null);
    }

    public RtrimMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("RTRIM(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
