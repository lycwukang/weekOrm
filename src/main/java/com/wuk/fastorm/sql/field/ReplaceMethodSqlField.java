package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * replace(str, from_str, to_str)
 */
public class ReplaceMethodSqlField extends AbstractSqlField {

    private SqlField str;
    private SqlField formStr;
    private SqlField toStr;

    public ReplaceMethodSqlField(String name, SqlField formStr, SqlField toStr) {
        this(new NameSqlField(name), formStr, toStr, null);
    }

    public ReplaceMethodSqlField(String name, SqlField formStr, SqlField toStr, String aliasName) {
        this(new NameSqlField(name), formStr, toStr, aliasName);
    }

    public ReplaceMethodSqlField(SqlField str, SqlField formStr, SqlField toStr) {
        this(str, formStr, toStr, null);
    }

    public ReplaceMethodSqlField(SqlField str, SqlField formStr, SqlField toStr, String aliasName) {
        super(aliasName);
        this.str = str;
        this.formStr = formStr;
        this.toStr = toStr;
    }

    @Override
    public String toSql() {
        return String.format("REPLACE(%s, %s, %s)", str.getSql(), formStr.getSql(), toStr.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(str, formStr, toStr);
    }
}
