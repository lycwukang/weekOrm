package com.wuk.fastorm.bean;

/**
 * 解析出{@link com.wuk.fastorm.annontation.FastormTable}和{@link com.wuk.fastorm.annontation.FastormColumn}信息
 */
public interface FastormBeanAnalyze {

    /**
     * 解析class
     * @param clazz
     * @param <T>
     * @return
     */
    <T> FastormBeanStructure<T> analyze(Class<T> clazz);

    /**
     * 解析BeanStructure
     * @param beanStructure
     * @param <T>
     * @return
     */
    <T> FastormBeanStructure<T> analyze(BeanStructure<T> beanStructure);
}
