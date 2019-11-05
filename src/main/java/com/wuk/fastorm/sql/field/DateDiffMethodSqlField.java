package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * date_diff(field, date2)
 */
public class DateDiffMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField date2;

    public DateDiffMethodSqlField(String name, SqlField date2) {
        this(new NameSqlField(name), date2, null);
    }

    public DateDiffMethodSqlField(SqlField field, SqlField date2) {
        this(field, date2, null);
    }

    public DateDiffMethodSqlField(String name, SqlField date2, String aliasName) {
        this(new NameSqlField(name), date2, aliasName);
    }

    public DateDiffMethodSqlField(SqlField field, SqlField date2, String aliasName) {
        super(aliasName);
        this.field = field;
        this.date2 = date2;
    }

    @Override
    public String toSql() {
        return String.format("DATEDIFF(%s, %s)", field.getSql(), date2.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, date2);
    }
}
