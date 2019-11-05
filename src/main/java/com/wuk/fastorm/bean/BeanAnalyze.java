package com.wuk.fastorm.bean;

/**
 * 对bean的字段和属性进行解析
 */
public interface BeanAnalyze {

    /**
     * 解析class，获得class的结构描述对象
     * @param clazz
     * @param <T>
     * @return
     */
    <T> BeanStructure<T> analyze(Class<T> clazz);
}
