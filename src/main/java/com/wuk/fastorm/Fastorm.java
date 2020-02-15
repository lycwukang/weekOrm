package com.wuk.fastorm;

import com.wuk.fastorm.data.ConnectionFindImpl;
import com.wuk.fastorm.data.ConnectionFindSource;
import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.impl.FastormSqlBuilder;
import com.wuk.fastorm.sql.impl.SimpleSqlBuilder;

import javax.sql.DataSource;
import java.sql.Connection;

public class Fastorm {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 构建FastormSqlBuilder
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> FastormSqlBuilder<T> build(Class<T> clazz) {
        return new FastormSession(new ConnectionFindSource(dataSource)).build(clazz);
    }

    /**
     * 构建SimpleSqlBuilder
     * @param sql
     * @return
     */
    public SimpleSqlBuilder build(String sql) {
        return new FastormSession(new ConnectionFindSource(dataSource)).build(sql);
    }

    /**
     * 构建FastormSqlBuilder，当FastormSession不为空时，使用FastormSession
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> FastormSqlBuilder<T> tryBuild(FastormSession session, Class<T> clazz) {
        if (session == null) {
            return build(clazz);
        } else {
            return session.build(clazz);
        }
    }

    /**
     * 构建SimpleSqlBuilder，当FastormSession不为空时，使用FastormSession
     * @param sql
     * @return
     */
    public SimpleSqlBuilder tryBuild(FastormSession session, String sql) {
        if (session == null) {
            return build(sql);
        } else {
            return session.build(sql);
        }
    }

    /**
     * 返回session对象，并设置成不自动提交（开启事务模式）
     * @return
     */
    public FastormSession begin() {
        FastormSession session = new FastormSession(new ConnectionFindImpl(getConnection()));
        session.setAutoCommit(false);
        return session;
    }

    /**
     * 获取新链接
     * @return
     */
    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new FastormSqlException("创建数据库链接出错", e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
