package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.SqlExpression;
import com.wuk.fastorm.sql.SqlParam;

import java.util.ArrayList;
import java.util.List;

public class StandardSqlSqlTextDeleteBuilder implements StandardSqlSqlTextBuilder {

    @Override
    public String toSql(StandardSql sql) {
        if (sql.getTable() == null || sql.getTable().length() == 0) {
            throw new FastormSqlException("构建sql出错，未声明table名称");
        }

        if (sql.getWhereList().size() == 0) {
            throw new FastormSqlException("构建sql出错，未设置whereList");
        }

        StringBuilder builder = new StringBuilder(String.format("%s FROM ", sql.getType().getSql()));

        if (sql.getSchema() != null && sql.getSchema().length() > 0) {
            builder.append(String.format("`%s`.", sql.getSchema()));
        }
        builder.append(String.format("`%s`", sql.getTable()));

        builder.append(" WHERE");
        for (int i = 0; i < sql.getWhereList().size(); i++) {
            builder.append(String.format(" %s", sql.getWhereList().get(i).getSql()));
        }

        return builder.toString();
    }

    @Override
    public List<SqlParam> findParams(StandardSql sql) {
        List<SqlParam> params = new ArrayList<>(6);

        for (SqlExpression expression : sql.getWhereList()) {
            params.addAll(expression.getParams());
        }

        return params;
    }
}
