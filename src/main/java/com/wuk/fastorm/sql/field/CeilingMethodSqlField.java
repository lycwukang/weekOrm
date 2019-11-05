package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * ceiling(field)
 */
public class CeilingMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public CeilingMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public CeilingMethodSqlField(SqlField field) {
        this(field, null);
    }

    public CeilingMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("CEILING(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
