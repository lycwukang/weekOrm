package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * second(field)
 */
public class SecondMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public SecondMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public SecondMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public SecondMethodSqlField(SqlField field) {
        this(field, null);
    }

    public SecondMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("SECOND(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
