package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * sum(field)
 */
public class SumMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public SumMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public SumMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public SumMethodSqlField(SqlField field) {
        this(field, null);
    }

    public SumMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("SUM(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
