package com.wuk.fastorm.data;

import com.wuk.fastorm.exception.FastormSqlException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFindSource implements ConnectionFind {

    private DataSource dataSource;
    private ConnectionFind find;

    public ConnectionFindSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection get() {
        if (find != null) {
            find.close();
            throw new FastormSqlException("已经创建过链接，不可重复创建");
        }

        try {
            find = new ConnectionFindImpl(dataSource.getConnection());
        } catch (SQLException e) {
            throw new FastormSqlException("创建链接出错");
        }

        return find.get();
    }

    @Override
    public boolean getAutoCommit() {
        return find.getAutoCommit();
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        find.setAutoCommit(autoCommit);
    }

    @Override
    public void close() {
        find.close();
    }

    @Override
    public void rollback() {
        find.rollback();
    }

    @Override
    public void commit() {
        find.commit();
    }
}
