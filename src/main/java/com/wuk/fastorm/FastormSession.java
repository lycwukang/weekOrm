package com.wuk.fastorm;

import com.wuk.fastorm.bean.DefaultFastormBeanAnalyze;
import com.wuk.fastorm.bean.FastormBeanStructure;
import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.impl.*;
import com.wuk.fastorm.sql.impl.SimpleSqlInternalBuilder;

import java.sql.Connection;

public class FastormSession implements AutoCloseable {

    private Connection connection;
    private boolean isCommit = false;
    private boolean isRollback = false;

    public FastormSession(Connection connection) {
        this.connection = connection;
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (Exception e) {
            throw new FastormSqlException("设置数据库autoCommit出错", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (Exception e) {
            throw new FastormSqlException("提交事务出错", e);
        }
        isCommit = true;
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (Exception e) {
            throw new FastormSqlException("回滚事务出错", e);
        }
        isRollback = true;
    }

    public <T> FastormSqlBuilder<T> build(Class<T> clazz) {
        FastormBeanStructure<T> beanStructure = new DefaultFastormBeanAnalyze().analyze(clazz);
        FastormSqlSessionExecutor<T> sqlSessionExecutor = new FastormSqlSessionExecutor<>(connection, beanStructure);
        FastormSqlInternalBuilder<T> sqlBuilder = FastormSqlInternalBuilder.instance(beanStructure, sqlSessionExecutor);
        sqlSessionExecutor.setSqlBuilder(sqlBuilder);
        return sqlBuilder;
    }

    public SimpleSqlBuilder build(String sql) {
        SimpleSqlSessionExecutor sqlExecutor = new SimpleSqlSessionExecutor(connection);
        SimpleSqlInternalBuilder sqlBuilder = SimpleSqlInternalBuilder.instance(sql, sqlExecutor);
        sqlExecutor.setSqlBuilder(sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public void close() throws Exception {
        if (!isCommit && !isRollback) {
            try {
                connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
