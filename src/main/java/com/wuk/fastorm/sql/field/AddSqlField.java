package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * field + x
 */
public class AddSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField increment;

    public AddSqlField(String name, SqlField increment) {
        this(new NameSqlField(name), increment, null);
    }

    public AddSqlField(SqlField field, SqlField increment) {
        this(field, increment, null);
    }

    public AddSqlField(String name, SqlField increment, String aliasName) {
        this(new NameSqlField(name), increment, aliasName);
    }

    public AddSqlField(SqlField field, SqlField increment, String aliasName) {
        super(aliasName);
        this.field = field;
        this.increment = increment;
    }

    @Override
    public String toSql() {
        return String.format("%s + %s", field.getSql(), increment.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, increment);
    }
}
