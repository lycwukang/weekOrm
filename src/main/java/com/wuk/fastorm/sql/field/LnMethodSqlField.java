package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * ln(field)
 */
public class LnMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public LnMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public LnMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public LnMethodSqlField(SqlField field) {
        this(field, null);
    }

    public LnMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("LN(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
