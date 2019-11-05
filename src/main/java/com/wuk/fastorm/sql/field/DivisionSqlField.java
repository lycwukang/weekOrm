package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * field / y
 */
public class DivisionSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField y;

    public DivisionSqlField(String name, SqlField y) {
        this(new NameSqlField(name), y, null);
    }

    public DivisionSqlField(String name, SqlField y, String aliasName) {
        this(new NameSqlField(name), y, aliasName);
    }

    public DivisionSqlField(SqlField field, SqlField y) {
        this(field, y, null);
    }

    public DivisionSqlField(SqlField field, SqlField y, String aliasName) {
        super(aliasName);
        this.field = field;
        this.y = y;
    }

    @Override
    public String toSql() {
        return String.format("%s / %s", field.getSql(), y.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, y);
    }
}
