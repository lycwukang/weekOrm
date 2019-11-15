package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.bean.BeanStructure;
import com.wuk.fastorm.exception.FastormSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class SimpleSqlSessionExecutor extends AbstractSqlSessionExecutor implements SimpleSqlExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSqlSessionExecutor.class);

    private Connection connection;

    public SimpleSqlSessionExecutor(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public void setSqlBuilder(SimpleSqlInternalBuilder sqlBuilder) {
        setSqlCreater(sqlBuilder);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public int exec() {
        return doUpdate();
    }

    @Override
    public int exec(SimpleGeneratedKey generatedKey) {
        return doUpdate(generatedKey);
    }

    @Override
    public <F> F read(Class<F> clazz) {
        List<F> ts = readList(clazz);

        return ts.size() > 0 ? ts.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <F> List<F> readList(Class<F> clazz) {
        try {
            return BeanStructure.readResultSet(doQuery(), clazz);
        } catch (Exception e) {
            throw new FastormSqlException("读取数据出错", e);
        }
    }
}
