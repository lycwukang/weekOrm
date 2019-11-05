package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.sql.SqlParam;

import java.util.List;

public interface StandardSqlSqlTextBuilder {

    /**
     * 生成sql语句
     * @param sql
     * @return
     */
    String toSql(StandardSql sql);

    /**
     * 获取sql字段中的变量部分
     * @param sql
     * @return
     */
    List<SqlParam> findParams(StandardSql sql);
}
