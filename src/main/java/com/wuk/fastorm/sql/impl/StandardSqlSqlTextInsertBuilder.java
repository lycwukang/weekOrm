package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlSet;

import java.util.ArrayList;
import java.util.List;

public class StandardSqlSqlTextInsertBuilder implements StandardSqlSqlTextBuilder {

    @Override
    public String toSql(StandardSql sql) {
        if (sql.getTable() == null || sql.getTable().length() == 0) {
            throw new FastormSqlException("构建sql出错，未声明table名称");
        }

        if (sql.getInsertDataList().size() == 0) {
            throw new FastormSqlException("构建sql出错，未设置insertDataList");
        }

        StringBuilder builder = new StringBuilder(String.format("%s INTO ", sql.getType()));

        if (sql.getSchema() != null && sql.getSchema().length() > 0) {
            builder.append(String.format("`%s`.", sql.getSchema()));
        }
        builder.append(String.format("`%s`", sql.getTable()));

        builder.append("(");
        for (int i = 0; i < sql.getInsertFieldList().size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(sql.getInsertFieldList().get(i).getSql());
        }
        builder.append(")");

        builder.append(" VALUES");
        for (int i = 0; i < sql.getInsertDataList().size(); i++) {
            if (i > 0) {
                builder.append(", ");
            }

            List<SqlParam> params = sql.getInsertDataList().get(i);
            builder.append("(");
            for (int j = 0; j < params.size(); j++) {
                if (j > 0) {
                    builder.append(", ");
                }
                builder.append("?");
            }
            builder.append(")");
        }

        if (sql.isOnDuplicateKeyUpdate()) {
            builder.append(" ON DUPLICATE KEY UPDATE ");

            for (int j = 0; j < sql.getSetList().size(); j++) {
                SqlSet set = sql.getSetList().get(j);
                if (j > 0) {
                    builder.append(", ");
                }
                builder.append(set.getSql());
            }
        }

        return builder.toString();
    }

    @Override
    public List<SqlParam> findParams(StandardSql sql) {
        List<SqlParam> params = new ArrayList<>(6);

        for (List<SqlParam> sqlParams : sql.getInsertDataList()) {
            params.addAll(sqlParams);
        }

        for (SqlSet sqlSet : sql.getSetList()) {
            params.addAll(sqlSet.getParams());
        }

        return params;
    }
}
