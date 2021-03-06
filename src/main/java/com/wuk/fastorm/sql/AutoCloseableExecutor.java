package com.wuk.fastorm.sql;

import com.wuk.fastorm.data.ConnectionFind;
import com.wuk.fastorm.sql.impl.SimpleGeneratedKey;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 允许自动管理链接
 */
public class AutoCloseableExecutor extends Executor implements AutoCloseable {

    public AutoCloseableExecutor(ConnectionFind connection, Sql sql) {
        super(connection, sql);
    }

    @Override
    public int doUpdate() throws SQLException {
        return super.doUpdate();
    }

    @Override
    public int doUpdate(SimpleGeneratedKey generatedKey) throws SQLException {
        return super.doUpdate(generatedKey);
    }

    @Override
    public ResultSet doQuery() throws SQLException {
        return super.doQuery();
    }

    @Override
    public void close() throws Exception {
        if (connection.getAutoCommit()) {
            // 自动提交事务的模式时关闭链接
            connection.close();
        }
    }
}
