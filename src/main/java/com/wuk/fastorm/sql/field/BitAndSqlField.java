package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * x & y
 */
public class BitAndSqlField extends AbstractSqlField {

    private SqlField x;
    private SqlField y;

    public BitAndSqlField(String name, SqlField y) {
        this(new NameSqlField(name), y, null);
    }

    public BitAndSqlField(String name, SqlField y, String aliasName) {
        this(new NameSqlField(name), y, aliasName);
    }

    public BitAndSqlField(SqlField x, SqlField y) {
        this(x, y, null);
    }

    public BitAndSqlField(SqlField x, SqlField y, String aliasName) {
        super(aliasName);
        this.x = x;
        this.y = y;
    }

    @Override
    public String toSql() {
        return String.format("%s & %s", x.getSql(), y.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(x, y);
    }
}
