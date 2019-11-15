package com.wuk.fastorm.sql.impl;

import java.util.List;

public class SimpleSqlBuilder implements SimpleSqlExecutor {

    /**
     * 静态sql
     */
    protected SimpleSql sql;

    /**
     * sql执行对象
     */
    protected SimpleSqlExecutor sqlExecutor;

    /**
     * 添加参数
     * @param name
     * @param obj
     */
    public SimpleSqlBuilder addParam(String name, Object obj) {
        sql.addParam(name, obj);

        return this;
    }

    @Override
    public int exec() {
        return sqlExecutor.exec();
    }

    @Override
    public int exec(SimpleGeneratedKey generatedKey) {
        return sqlExecutor.exec(generatedKey);
    }

    @Override
    public <F> F read(Class<F> clazz) {
        return sqlExecutor.read(clazz);
    }

    @Override
    public <F> List<F> readList(Class<F> clazz) {
        return sqlExecutor.readList(clazz);
    }
}
