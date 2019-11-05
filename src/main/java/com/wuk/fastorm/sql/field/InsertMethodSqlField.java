package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * insert(field, pos, len, newStr)
 */
public class InsertMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private int pos;
    private int len;
    private SqlField newStr;

    public InsertMethodSqlField(String name, int pos, int len, SqlField newStr) {
        this(new NameSqlField(name), pos, len, newStr, null);
    }

    public InsertMethodSqlField(String name, int pos, int len, SqlField newStr, String aliasName) {
        this(new NameSqlField(name), pos, len, newStr, aliasName);
    }

    public InsertMethodSqlField(SqlField field, int pos, int len, SqlField newStr) {
        this(field, pos, len, newStr, null);
    }

    public InsertMethodSqlField(SqlField field, int pos, int len, SqlField newStr, String aliasName) {
        super(aliasName);
        this.field = field;
        this.pos = pos;
        this.len = len;
        this.newStr = newStr;
    }

    @Override
    public String toSql() {
        return String.format("INSERT(%s, %d, %d, %s)", field.getSql(), pos, len, newStr.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field, newStr);
    }
}
