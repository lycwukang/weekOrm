package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * ltrim(field)
 */
public class LtrimMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public LtrimMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public LtrimMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public LtrimMethodSqlField(SqlField field) {
        this(field, null);
    }

    public LtrimMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("LTRIM(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
