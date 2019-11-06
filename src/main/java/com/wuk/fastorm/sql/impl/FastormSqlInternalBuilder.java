package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.bean.FastormBeanStructure;
import com.wuk.fastorm.sql.Sql;
import com.wuk.fastorm.sql.SqlCreater;

/**
 * 增加SqlCreater接口
 * @param <T>
 */
public class FastormSqlInternalBuilder<T> extends FastormSqlBuilder<T> implements SqlCreater {

    /**
     * 初始化新实例
     * @param beanStructure
     * @param sqlExecutor
     * @param <T>
     * @return
     */
    public static <T> FastormSqlInternalBuilder<T> instance(FastormBeanStructure<T> beanStructure, FastormSqlExecutor<T> sqlExecutor) {
        FastormSqlInternalBuilder<T> sqlBuilder = new FastormSqlInternalBuilder<>();
        sqlBuilder.beanStructure = beanStructure;
        sqlBuilder.sql = new StandardSql();
        sqlBuilder.sql.setSchema(sqlBuilder.beanStructure.getTable().schema());
        sqlBuilder.sql.setTable(sqlBuilder.beanStructure.getTable().value());
        sqlBuilder.sqlExecutor = sqlExecutor;

        return sqlBuilder;
    }

    @Override
    public Sql createSql() {
        return sql;
    }

    /**
     * 获取等待写入的数据
     * @return
     */
    public T getFirstInsertData() {
        return insertFirstData;
    }
}
