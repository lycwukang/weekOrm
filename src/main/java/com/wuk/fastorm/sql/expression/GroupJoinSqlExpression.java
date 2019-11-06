package com.wuk.fastorm.sql.expression;

import com.wuk.fastorm.lang.StringUtils;
import com.wuk.fastorm.sql.SqlExpression;
import com.wuk.fastorm.sql.SqlJoinType;
import com.wuk.fastorm.sql.SqlParam;

import java.util.ArrayList;
import java.util.List;

public class GroupJoinSqlExpression extends ArrayList<SqlExpression> implements SqlExpression {

    private SqlJoinType joinType;

    public void setJoinType(SqlJoinType joinType) {
        this.joinType = joinType;
    }

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
        StringBuilder builder = new StringBuilder();
        if (size() > 0) {
            StringBuilder builder2 = new StringBuilder();
            for (int i = 0; i < this.size(); i++) {
                if (i > 0) {
                    builder2.append(" ");
                }
                builder2.append(this.get(i).getSql());
            }

            if (StringUtils.isNotEmpty(joinType.getSql())) {
                builder.append(String.format("%s ", joinType.getSql()));
            }
            builder.append(String.format("(%s)", builder2.toString()));
        }
        return builder.toString();
    }
}
