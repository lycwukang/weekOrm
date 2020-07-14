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
     * 保存当前线程中的session对象
     */
    ThreadLocal<FastormSession> localSession = new ThreadLocal<>();

    /**
     * 构建FastormSqlBuilder
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> FastormSqlBuilder<T> build(Class<T> clazz) {
        FastormSession session = localSession.get();
        if (session == null) {
            session = new FastormSession(new ConnectionFindSource(dataSource), this);
        }
        return session.build(clazz);
    }

    /**
     * 构建SimpleSqlBuilder
     * @param sql
     * @return
     */
    public SimpleSqlBuilder build(String sql) {
        FastormSession session = localSession.get();
        if (session == null) {
            session = new FastormSession(new ConnectionFindSource(dataSource), this);
        }
        return session.build(sql);
    }

    /**
     * 构建FastormSqlBuilder，当FastormSession不为空时，使用FastormSession
     * @param clazz
     * @param <T>
     * @return
     */
    @Deprecated
    public <T> FastormSqlBuilder<T> tryBuild(FastormSession session, Class<T> clazz) {
        if (session == null) {
            return build(clazz);
        }

        FastormSession session0 = localSession.get();
        if (session0 == null || session0 != session) {
            throw new IllegalArgumentException("session不是由此对象创建，请检查");
        }
        return session.build(clazz);
    }

    /**
     * 构建SimpleSqlBuilder，当FastormSession不为空时，使用FastormSession
     * @param sql
     * @return
     */
    @Deprecated
    public SimpleSqlBuilder tryBuild(FastormSession session, String sql) {
        if (session == null) {
            return build(sql);
        }

        FastormSession session0 = localSession.get();
        if (session0 == null || session0 != session) {
            throw new IllegalArgumentException("session不是由此对象创建，请检查");
        }
        return session.build(sql);
    }

    /**
     * 返回session对象，并设置成不自动提交（开启事务模式）
     * @return
     */
    public FastormSession begin() {
        return begin(false);
    }

    /**
     * 返回session对象，选择提交模式（开启事务模式）
     * @param autoCommit
     * @return
     */
    public FastormSession begin(boolean autoCommit) {
        FastormSession session = new FastormSession(new ConnectionFindImpl(getConnection()), this);
        session.setAutoCommit(autoCommit);

        // 保存到线程中
        localSession.set(session);

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
