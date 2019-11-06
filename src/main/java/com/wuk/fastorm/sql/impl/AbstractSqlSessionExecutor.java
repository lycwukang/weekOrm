package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.AutoCloseableExecutor;
import com.wuk.fastorm.sql.Sql;
import com.wuk.fastorm.sql.SqlCreater;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class AbstractSqlSessionExecutor {

    private Connection connection;
    private SqlCreater sqlCreater;

    public AbstractSqlSessionExecutor(Connection connection) {
        this.connection = connection;
    }

    public void setSqlCreater(SqlCreater sqlCreater) {
        this.sqlCreater = sqlCreater;
    }

    public abstract Logger getLogger();

    public int doUpdate() {
        return doUpdate(null);
    }

    public int doUpdate(SimpleGeneratedKey generatedKey) {
        Sql sql = sqlCreater.createSql();

        if (getLogger().isDebugEnabled()) {
            getLogger().debug(String.format("sql: %s", sql.getSql()));
        }

        int result;
        try (AutoCloseableExecutor executor = new AutoCloseableExecutor(connection, sql)) {
            if (generatedKey != null) {
                result = executor.doUpdate(generatedKey);
            } else {
                result = executor.doUpdate();
            }
        } catch (Exception e) {
            throw new FastormSqlException("执行sql出错", e);
        }
        return result;
    }

    public ResultSet doQuery() {
        Sql sql = sqlCreater.createSql();

        if (getLogger().isDebugEnabled()) {
            getLogger().debug(String.format("sql: %s", sql.getSql()));
        }

        try (AutoCloseableExecutor executor = new AutoCloseableExecutor(connection, sql)) {
            return executor.doQuery();
        } catch (Exception e) {
            throw new FastormSqlException("读取数据出错", e);
        }
    }
}
