package com.wuk.fastorm.sql.expression;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * x = y
 */
public class EqualSqlExpression extends AbstractSqlExpression {

    private SqlField x;
    private SqlField y;

    public EqualSqlExpression(SqlField x, SqlField y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getSql() {
        return String.format("%s = %s", x.getSql(), y.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(x, y);
    }
}
