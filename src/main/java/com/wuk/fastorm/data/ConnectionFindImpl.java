package com.wuk.fastorm.data;

import com.wuk.fastorm.exception.FastormSqlException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFindImpl implements ConnectionFind {

    private Connection connection;
    private boolean isCommit = false;
    private boolean isRollback = false;

    public ConnectionFindImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection get() {
        return connection;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new FastormSqlException("设置数据库自动提交出错", e);
        }
    }

    @Override
    public boolean getAutoCommit() {
        try {
            return connection.getAutoCommit();
        } catch (SQLException e) {
            throw new FastormSqlException("查询数据库自动提交出错", e);
        }
    }

    @Override
    public void close() {
        if (!isCommit && !isRollback) {
            rollback();
        }

        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new FastormSqlException("关闭链接出错", e);
        }
    }

    @Override
    public void rollback() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (Exception e) {
            throw new FastormSqlException("回滚事务出错", e);
        }
        isRollback = true;
    }

    @Override
    public void commit() {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (Exception e) {
            throw new FastormSqlException("提交事务出错", e);
        }
        isCommit = true;
    }
}
