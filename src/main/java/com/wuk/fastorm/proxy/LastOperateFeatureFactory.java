package com.wuk.fastorm.proxy;

import com.wuk.fastorm.bean.BeanStructure;

/**
 * LastOperateFeature接口实例的创建工厂，
 * 想要查看更多详细信息，请跳转{@link com.wuk.fastorm.proxy.LastOperateFeature}
 */
public interface LastOperateFeatureFactory {

    /**
     * 创建实现了LastOperateFeature接口的T的代理实例
     * @param beanStructure
     * @param <T>
     * @return
     */
    <T> T instance(BeanStructure<T> beanStructure);

    /**
     * 创建实现了LastOperateFeature接口的T的代理实例
     * @param t
     * @param beanStructure
     * @param <T>
     * @return
     */
    <T> T instance(T t, BeanStructure<T> beanStructure);
}
