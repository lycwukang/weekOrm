package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * timestamp_diff(unit, field, date2)
 */
public class TimestampDiffMethodSqlField extends AbstractSqlField {

    private SqlDateUnit unit;
    private SqlField field;
    private SqlField date2;

    public TimestampDiffMethodSqlField(SqlDateUnit unit, String name, SqlField date2) {
        this(unit, new NameSqlField(name), date2, null);
    }

    public TimestampDiffMethodSqlField(SqlDateUnit unit, String name, SqlField date2, String aliasName) {
        this(unit, new NameSqlField(name), date2, aliasName);
    }

    public TimestampDiffMethodSqlField(SqlDateUnit unit, SqlField field, SqlField date2) {
        this(unit, field, date2, null);
    }

    public TimestampDiffMethodSqlField(SqlDateUnit unit, SqlField field, SqlField date2, String aliasName) {
        super(aliasName);
        this.unit = unit;
        this.field = field;
        this.date2 = date2;
    }

    @Override
    public String toSql() {
        return String.format("TIMESTAMPDIFF(%s, %s, %s)", unit.getSql(), field.getSql(), date2.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, date2);
    }
}
