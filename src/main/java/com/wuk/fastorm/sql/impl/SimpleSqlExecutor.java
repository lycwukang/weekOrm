package com.wuk.fastorm.sql.impl;

import java.util.List;

public interface SimpleSqlExecutor {

    /**
     * 执行更新
     * @return
     */
    int exec();

    /**
     * 执行更新，获取自增长主键
     * @param generatedKey
     * @return
     */
    int exec(SimpleGeneratedKey generatedKey);

    /**
     * 读取一条数据
     * @param clazz
     * @return
     */
    <F> F read(Class<F> clazz);

    /**
     * 读取全部数据
     * @param clazz
     * @return
     */
    <F> List<F> readList(Class<F> clazz);
}
