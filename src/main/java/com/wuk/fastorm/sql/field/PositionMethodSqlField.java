package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * position(subStr in str)
 */
public class PositionMethodSqlField extends AbstractSqlField {

    private SqlField subStr;
    private SqlField str;

    public PositionMethodSqlField(String name, SqlField str) {
        this(new NameSqlField(name), str, null);
    }

    public PositionMethodSqlField(String name, SqlField str, String aliasName) {
        this(new NameSqlField(name), str, aliasName);
    }

    public PositionMethodSqlField(SqlField subStr, SqlField str) {
        this(subStr, str, null);
    }

    public PositionMethodSqlField(SqlField subStr, SqlField str, String aliasName) {
        super(aliasName);
        this.subStr = subStr;
        this.str = str;
    }

    @Override
    public String toSql() {
        return String.format("POSITION(%s IN %s)", subStr.getSql(), str.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(subStr, str);
    }
}
