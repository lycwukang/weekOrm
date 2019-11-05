package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * length(field)
 */
public class LengthMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public LengthMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public LengthMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public LengthMethodSqlField(SqlField field) {
        this(field, null);
    }

    public LengthMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("LENGTH(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
