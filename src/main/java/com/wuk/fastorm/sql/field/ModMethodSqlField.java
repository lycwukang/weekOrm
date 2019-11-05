package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * mod(field, y)
 */
public class ModMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField y;

    public ModMethodSqlField(String name, SqlField y) {
        this(new NameSqlField(name), y, null);
    }

    public ModMethodSqlField(String name, SqlField y, String aliasName) {
        this(new NameSqlField(name), y, aliasName);
    }

    public ModMethodSqlField(SqlField field, SqlField y) {
        this(field, y, null);
    }

    public ModMethodSqlField(SqlField field, SqlField y, String aliasName) {
        super(aliasName);
        this.field = field;
        this.y = y;
    }

    @Override
    public String toSql() {
        return String.format("mod(%s, %s)", field.getSql(), y.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, y);
    }
}
