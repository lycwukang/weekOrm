package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * min(field)
 */
public class MinMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public MinMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public MinMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public MinMethodSqlField(SqlField field) {
        this(field, null);
    }

    public MinMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("MIN(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
