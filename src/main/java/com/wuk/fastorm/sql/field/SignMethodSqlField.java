package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * sign(field)
 */
public class SignMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public SignMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public SignMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public SignMethodSqlField(SqlField field) {
        this(field, null);
    }

    public SignMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("SIGN(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
