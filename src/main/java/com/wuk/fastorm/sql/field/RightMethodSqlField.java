package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * RIGHT(field, length)
 */
public class RightMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private int length;

    public RightMethodSqlField(String name, int length) {
        this(new NameSqlField(name), length, null);
    }

    public RightMethodSqlField(String name, int length, String aliasName) {
        this(new NameSqlField(name), length, aliasName);
    }

    public RightMethodSqlField(SqlField field, int length) {
        this(field, length, null);
    }

    public RightMethodSqlField(SqlField field, int length, String aliasName) {
        super(aliasName);
        this.field = field;
        this.length = length;
    }

    @Override
    public String toSql() {
        return String.format("RIGHT(%s, %d)", field.getSql(), length);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
