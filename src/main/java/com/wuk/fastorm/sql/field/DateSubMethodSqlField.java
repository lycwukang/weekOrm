package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * date_sub(field, interval expr unit)
 */
public class DateSubMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private int expr;
    private SqlDateUnit unit;

    public DateSubMethodSqlField(String name, int expr, SqlDateUnit unit) {
        this(new NameSqlField(name), expr, unit, null);
    }

    public DateSubMethodSqlField(String name, int expr, SqlDateUnit unit, String aliasName) {
        this(new NameSqlField(name), expr, unit, aliasName);
    }

    public DateSubMethodSqlField(SqlField field, int expr, SqlDateUnit unit) {
        this(field, expr, unit, null);
    }

    public DateSubMethodSqlField(SqlField field, int expr, SqlDateUnit unit, String aliasName) {
        super(aliasName);
        this.field = field;
        this.expr = expr;
        this.unit = unit;
    }

    @Override
    public String toSql() {
        return String.format("DATE_SUB(%s, INTERVAL %d %s", field.getSql(), expr, unit.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
