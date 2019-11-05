package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * week(date)
 */
public class WeekMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public WeekMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public WeekMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public WeekMethodSqlField(SqlField field) {
        this(field, null);
    }

    public WeekMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("WEEK(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
