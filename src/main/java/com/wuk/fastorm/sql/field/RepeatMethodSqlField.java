package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * repeat(field, count)
 */
public class RepeatMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private int count;

    public RepeatMethodSqlField(String name, int count) {
        this(new NameSqlField(name), count, null);
    }

    public RepeatMethodSqlField(String name, int count, String aliasName) {
        this(new NameSqlField(name), count, aliasName);
    }

    public RepeatMethodSqlField(SqlField field, int count) {
        this(field, count, null);
    }

    public RepeatMethodSqlField(SqlField field, int count, String aliasName) {
        super(aliasName);
        this.field = field;
        this.count = count;
    }

    @Override
    public String toSql() {
        return String.format("REPEAT(%s, %d)", field.getSql(), count);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
