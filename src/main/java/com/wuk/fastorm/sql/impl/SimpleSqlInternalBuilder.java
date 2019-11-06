package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.Sql;
import com.wuk.fastorm.sql.SqlCreater;

public class SimpleSqlInternalBuilder extends SimpleSqlBuilder implements SqlCreater {

    /**
     * 初始化新实例
     * @param sql
     * @return
     */
    public static SimpleSqlInternalBuilder instance(String sql, SimpleSqlExecutor sqlExecutor) {
        SimpleSqlInternalBuilder sqlBuilder = new SimpleSqlInternalBuilder();
        sqlBuilder.sql = new SimpleSql(sql);
        sqlBuilder.sqlExecutor = sqlExecutor;

        return sqlBuilder;
    }

    @Override
    public Sql createSql() {
        return sql;
    }
}
