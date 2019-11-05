package com.wuk.fastorm;

import com.wuk.fastorm.bean.DefaultFastormBeanAnalyze;
import com.wuk.fastorm.bean.FastormBeanStructure;
import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.impl.*;
import com.wuk.fastorm.sql.impl.SimpleSqlBuilder2;

import java.sql.Connection;

public class FastormSession implements AutoCloseable {

    private Connection connection;

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
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (Exception e) {
            throw new FastormSqlException("回滚事务出错", e);
        }
    }

    public <T> FastormSqlBuilder<T> build(Class<T> clazz) {
        FastormBeanStructure<T> beanStructure = new DefaultFastormBeanAnalyze().analyze(clazz);
        FastormSqlSessionExecutor<T> sqlSessionExecutor = new FastormSqlSessionExecutor<>(connection, beanStructure);
        FastormSqlBuilder2<T> sqlBuilder = FastormSqlBuilder2.instance(beanStructure, sqlSessionExecutor);
        sqlSessionExecutor.setSqlBuilder(sqlBuilder);
        return sqlBuilder;
    }

    public SimpleSqlBuilder build(String sql) {
        SimpleSqlSessionExecutor sqlExecutor = new SimpleSqlSessionExecutor(connection);
        SimpleSqlBuilder2 sqlBuilder = SimpleSqlBuilder2.instance(sql, sqlExecutor);
        sqlExecutor.setSqlBuilder(sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public void close() throws Exception {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw new FastormSqlException("自动关闭数据库链接出错", e);
        }
    }
}
