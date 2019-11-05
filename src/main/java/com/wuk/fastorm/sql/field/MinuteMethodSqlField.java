package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * minute(date)
 */
public class MinuteMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public MinuteMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public MinuteMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public MinuteMethodSqlField(SqlField field) {
        this(field, null);
    }

    public MinuteMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("MINUTE(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
