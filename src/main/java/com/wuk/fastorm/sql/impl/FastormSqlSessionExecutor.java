package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.bean.BeanStructure;
import com.wuk.fastorm.bean.FastormBeanLastOperateStructure;
import com.wuk.fastorm.data.ConnectionFind;
import com.wuk.fastorm.exception.FastormSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class FastormSqlSessionExecutor<T> extends AbstractSqlSessionExecutor implements FastormSqlExecutor<T> {

    private static final Logger logger = LoggerFactory.getLogger(FastormSqlSessionExecutor.class);

    private FastormBeanLastOperateStructure<T> beanStructure;
    private FastormSqlInternalBuilder<T> sqlBuilder;

    public FastormSqlSessionExecutor(ConnectionFind connection, FastormBeanLastOperateStructure<T> beanStructure) {
        super(connection);
        this.beanStructure = beanStructure;
    }

    public void setSqlBuilder(FastormSqlInternalBuilder<T> sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
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
    public int exec(FastormGeneratedKey<T> generatedKey) {
        SimpleGeneratedKey simpleGeneratedKey = new SimpleGeneratedKey();
        int result = doUpdate(simpleGeneratedKey);

        T t = sqlBuilder.getFirstInsertData();
        if (t != null) {
            String fieldName = beanStructure.findFieldName(generatedKey.getFunction());
            Method readMethod = beanStructure.getReadMethodMap().get(fieldName);
            Method writeMethod = beanStructure.getWriteMethodMap().get(fieldName);

            Object val = null;
            if (readMethod.getReturnType().equals(int.class) || readMethod.getReturnType().equals(Integer.class)) {
                val = simpleGeneratedKey.getInt();
            } else if (readMethod.getReturnType().equals(long.class) || readMethod.getReturnType().equals(Long.class)) {
                val = simpleGeneratedKey.getLong();
            }

            try {
                writeMethod.invoke(t, val);
            } catch (Exception e) {
                throw new FastormSqlException("设置自增长值出错", e);
            }
        }

        return result;
    }

    @Override
    public T read() {
        List<T> ts = readList();

        return ts.size() > 0 ? ts.get(0) : null;
    }

    @Override
    public List<T> readList() {
        try {
            return beanStructure.readResultSet(doQuery());
        } catch (Exception e) {
            throw new FastormSqlException("读取数据出错", e);
        }
    }

    @Override
    public <F> F read(Class<F> clazz) {
        List<F> ts = readList(clazz);

        return ts.size() > 0 ? ts.get(0) : null;
    }

    @Override
    public <F> List<F> readList(Class<F> clazz) {
        try {
            return BeanStructure.readResultSet(doQuery(), clazz);
        } catch (Exception e) {
            throw new FastormSqlException("读取数据出错", e);
        }
    }
}
