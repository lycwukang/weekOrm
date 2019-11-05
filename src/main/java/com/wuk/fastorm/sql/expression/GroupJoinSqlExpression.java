package com.wuk.fastorm.sql.expression;

import com.wuk.fastorm.sql.SqlExpression;
import com.wuk.fastorm.sql.SqlParam;

import java.util.ArrayList;
import java.util.List;

public class GroupJoinSqlExpression extends ArrayList<SqlExpression> implements SqlExpression {

    @Override
    public List<SqlParam> getParams() {
        List<SqlParam> variableParts = new ArrayList<>();
        for (SqlExpression expression : this) {
            variableParts.addAll(expression.getParams());
        }
        return variableParts;
    }

    @Override
    public String getSql() {
        if (size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (SqlExpression expression : this) {
                builder.append(expression.getSql());
            }
            return String.format("(%s)", builder.toString());
        }
        return "";
    }
}
