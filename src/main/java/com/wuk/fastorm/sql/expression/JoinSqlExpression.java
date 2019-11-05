package com.wuk.fastorm.sql.expression;

import com.wuk.fastorm.sql.SqlExpression;
import com.wuk.fastorm.sql.SqlJoinType;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

public class JoinSqlExpression extends AbstractSqlExpression {

    private SqlJoinType joinType;
    private SqlExpression expression;

    public JoinSqlExpression(SqlJoinType joinType, SqlExpression expression) {
        this.joinType = joinType;
        this.expression = expression;
    }

    @Override
    public String getSql() {
        if (joinType.equals(SqlJoinType.EMPTY)) {
            return expression.getSql();
        } else {
            return String.format("%s %s", joinType, expression.getSql());
        }
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(expression);
    }
}
