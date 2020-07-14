package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.SqlExpression;
import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlOrder;
import com.wuk.fastorm.sql.SqlParam;

import java.util.ArrayList;
import java.util.List;

public class StandardSqlSqlTextSelectBuilder implements StandardSqlSqlTextBuilder {

    @Override
    public String toSql(StandardSql sql) {
        if (sql.getTable() == null || sql.getTable().length() == 0) {
            throw new FastormSqlException("构建sql出错，未声明table名称");
        }

        if (sql.getFieldList().size() == 0) {
            throw new FastormSqlException("构建sql出错，没有任何要查询的字段");
        }

        StringBuilder builder = new StringBuilder(String.format("%s ", sql.getType().getSql()));

        for (int i = 0; i < sql.getFieldList().size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(sql.getFieldList().get(i).getSql());
        }
        builder.append(" FROM ");

        if (sql.getSchema() != null && sql.getSchema().length() > 0) {
            builder.append(String.format("`%s`.", sql.getSchema()));
        }
        builder.append(String.format("`%s`", sql.getTable()));

        if (sql.getWhereList().size() > 0) {
            builder.append(" WHERE ");

            for (int i = 0; i < sql.getWhereList().size(); i++) {
                if (i > 0) {
                    builder.append(" ");
                }
                builder.append(sql.getWhereList().get(i).getSql());
            }
        }

        if (sql.getGroupFieldList().size() > 0) {
            builder.append(" GROUP BY ");

            for (int i = 0; i < sql.getGroupFieldList().size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(sql.getGroupFieldList().get(i).getSql());
            }
        }

        if (sql.getHavingList().size() > 0) {
            builder.append(" HAVING ");

            for (int i = 0; i < sql.getHavingList().size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(sql.getHavingList().get(i).getSql());
            }
        }

        if (sql.getOrderFieldList().size() > 0) {
            builder.append(" ORDER BY ");

            for (int i = 0; i < sql.getOrderFieldList().size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(sql.getOrderFieldList().get(i).getSql());
            }
        }

        if (sql.getLength() > 0) {
            builder.append(String.format(" LIMIT %d, %d", sql.getIndex(), sql.getLength()));
        }

        return builder.toString();
    }

    @Override
    public List<SqlParam> findParams(StandardSql sql) {
        List<SqlParam> params = new ArrayList<>(6);

        for (SqlField field : sql.getFieldList()) {
            params.addAll(field.getParams());
        }

        for (SqlExpression expression : sql.getWhereList()) {
            params.addAll(expression.getParams());
        }

        for (SqlField field : sql.getGroupFieldList()) {
            params.addAll(field.getParams());
        }

        for (SqlExpression expression : sql.getHavingList()) {
            params.addAll(expression.getParams());
        }

        for (SqlOrder sqlOrder : sql.getOrderFieldList()) {
            params.addAll(sqlOrder.getParams());
        }

        return params;
    }
}
