package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.Sql;
import com.wuk.fastorm.sql.SqlCreater;

public class SimpleSqlBuilder2 extends SimpleSqlBuilder implements SqlCreater {

    /**
     * 初始化新实例
     * @param sql
     * @return
     */
    public static SimpleSqlBuilder2 instance(String sql, SimpleSqlExecutor sqlExecutor) {
        SimpleSqlBuilder2 sqlBuilder = new SimpleSqlBuilder2();
        sqlBuilder.sql = new SimpleSql(sql);
        sqlBuilder.sqlExecutor = sqlExecutor;

        return sqlBuilder;
    }

    @Override
    public Sql createSql() {
        return sql;
    }
}
