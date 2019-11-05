package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * hour(field)
 */
public class HourMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public HourMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public HourMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public HourMethodSqlField(SqlField field) {
        this(field, null);
    }

    public HourMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("HOUR(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
