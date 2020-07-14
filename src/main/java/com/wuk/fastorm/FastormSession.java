package com.wuk.fastorm;

import com.wuk.fastorm.bean.DefaultFastormBeanAnalyze;
import com.wuk.fastorm.bean.FastormBeanLastOperateStructure;
import com.wuk.fastorm.data.ConnectionFind;
import com.wuk.fastorm.sql.impl.*;
import com.wuk.fastorm.sql.impl.SimpleSqlInternalBuilder;

public class FastormSession implements AutoCloseable {

    private ConnectionFind find;
    private Fastorm fastorm;

    public FastormSession(ConnectionFind find, Fastorm fastorm) {
        this.find = find;
        this.fastorm = fastorm;
    }

    public void setAutoCommit(boolean autoCommit) {
        find.setAutoCommit(autoCommit);
    }

    public void commit() {
        find.commit();
    }

    public void rollback() {
        find.rollback();
    }

    public <T> FastormSqlBuilder<T> build(Class<T> clazz) {
        FastormBeanLastOperateStructure<T> beanStructure = new DefaultFastormBeanAnalyze().analyze(clazz);
        FastormSqlSessionExecutor<T> sqlSessionExecutor = new FastormSqlSessionExecutor<>(find, beanStructure);
        FastormSqlInternalBuilder<T> sqlBuilder = FastormSqlInternalBuilder.instance(beanStructure, sqlSessionExecutor);
        sqlSessionExecutor.setSqlBuilder(sqlBuilder);
        return sqlBuilder;
    }

    public SimpleSqlBuilder build(String sql) {
        SimpleSqlSessionExecutor sqlExecutor = new SimpleSqlSessionExecutor(find);
        SimpleSqlInternalBuilder sqlBuilder = SimpleSqlInternalBuilder.instance(sql, sqlExecutor);
        sqlExecutor.setSqlBuilder(sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public void close() throws Exception {
        try {
            fastorm.localSession.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }

        find.close();
    }
}
