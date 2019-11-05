package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * month(date)
 */
public class MonthMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public MonthMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public MonthMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public MonthMethodSqlField(SqlField field) {
        this(field, null);
    }

    public MonthMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("MONTH(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
