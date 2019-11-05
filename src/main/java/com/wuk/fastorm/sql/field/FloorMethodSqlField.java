package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * floor(field)
 */
public class FloorMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public FloorMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public FloorMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public FloorMethodSqlField(SqlField field) {
        this(field, null);
    }

    public FloorMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("FLOOR(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
