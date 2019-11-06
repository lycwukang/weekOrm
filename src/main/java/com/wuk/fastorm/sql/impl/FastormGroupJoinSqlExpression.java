package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.SqlExpression;
import com.wuk.fastorm.sql.expression.GroupJoinSqlExpression;

import java.util.function.Consumer;

public class FastormGroupJoinSqlExpression {

    private Consumer<SqlExpression> consumer;
    private GroupJoinSqlExpression expressions;

    public void join() {
        consumer.accept(expressions);
    }

    public Consumer<SqlExpression> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<SqlExpression> consumer) {
        this.consumer = consumer;
    }

    public GroupJoinSqlExpression getExpressions() {
        return expressions;
    }

    public void setExpressions(GroupJoinSqlExpression expressions) {
        this.expressions = expressions;
    }
}
