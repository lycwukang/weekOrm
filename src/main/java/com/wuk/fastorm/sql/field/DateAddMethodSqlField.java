package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * date_add(field, interval expr unit)
 */
public class DateAddMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField expr;
    private SqlDateUnit unit;

    public DateAddMethodSqlField(String name, SqlField expr, SqlDateUnit unit) {
        this(new NameSqlField(name), expr, unit, null);
    }

    public DateAddMethodSqlField(SqlField field, SqlField expr, SqlDateUnit unit) {
        this(field, expr, unit, null);
    }

    public DateAddMethodSqlField(String name, SqlField expr, SqlDateUnit unit, String aliasName) {
        this(new NameSqlField(name), expr, unit, aliasName);
    }

    public DateAddMethodSqlField(SqlField field, SqlField expr, SqlDateUnit unit, String aliasName) {
        super(aliasName);
        this.field = field;
        this.expr = expr;
        this.unit = unit;
    }

    @Override
    public String toSql() {
        return String.format("DATE_ADD(%s, INTERVAL %s %s", field.getSql(), expr.getSql(), unit.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, expr);
    }
}
