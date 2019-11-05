package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * log(field, y)
 */
public class LogMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField y;

    public LogMethodSqlField(String name, SqlField y) {
        this(new NameSqlField(name), y, null);
    }

    public LogMethodSqlField(String name, SqlField y, String aliasName) {
        this(new NameSqlField(name), y, aliasName);
    }

    public LogMethodSqlField(SqlField field, SqlField y) {
        this(field, y, null);
    }

    public LogMethodSqlField(SqlField field, SqlField y, String aliasName) {
        super(aliasName);
        this.field = field;
        this.y = y;
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, y);
    }

    @Override
    public String toSql() {
        return String.format("LOG(%s, %s)", field.getSql(), y.getSql());
    }
}
