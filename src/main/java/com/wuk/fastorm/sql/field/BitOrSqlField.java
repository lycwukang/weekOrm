package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * x | y
 */
public class BitOrSqlField extends AbstractSqlField {

    private SqlField x;
    private SqlField y;
    private String aliasName;

    public BitOrSqlField(String name, SqlField y) {
        this(new NameSqlField(name), y, null);
    }

    public BitOrSqlField(String name, SqlField y, String aliasName) {
        this(new NameSqlField(name), y, aliasName);
    }

    public BitOrSqlField(SqlField x, SqlField y) {
        this(x, y, null);
    }

    public BitOrSqlField(SqlField x, SqlField y, String aliasName) {
        super(aliasName);
        this.x = x;
        this.y = y;
    }

    @Override
    public String toSql() {
        return String.format("%s | %s", x.getSql(), y.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(x, y);
    }
}
