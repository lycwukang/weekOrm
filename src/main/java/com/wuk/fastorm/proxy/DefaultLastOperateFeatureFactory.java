package com.wuk.fastorm.proxy;

import com.wuk.fastorm.bean.BeanStructure;
import com.wuk.fastorm.exception.FastormException;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的LastOperateFeatureFactory实现
 */
public class DefaultLastOperateFeatureFactory implements LastOperateFeatureFactory {

    /**
     * 缓存已经创建的class对象
     */
    private static final Map<Class<?>, LastOperateFeatureInstanceClassLoader<?>> INSTANCE_CLASS_MAP = new ConcurrentHashMap<>(12);

    @Override
    public <T> T instance(BeanStructure<T> beanStructure) {
        Constructor<T> constructor;
        try {
            constructor = beanStructure.getClazz().getDeclaredConstructor();
        } catch (Exception e) {
            throw new FastormException("创建Class实例出错", e);
        }

        T obj;
        try {
            obj = constructor.newInstance();
        } catch (Exception e) {
            throw new FastormException("创建Class实例出错", e);
        }

        return instance(obj, beanStructure);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T instance(T t, BeanStructure<T> beanStructure) {
        if (!INSTANCE_CLASS_MAP.containsKey(beanStructure.getClazz())) {
            INSTANCE_CLASS_MAP.putIfAbsent(beanStructure.getClazz(), new LastOperateFeatureInstanceClassLoader<>(beanStructure));
        }
        Class<T> instanceClazz = (Class<T>) INSTANCE_CLASS_MAP.get(beanStructure.getClazz()).getInstanceClass();

        Constructor<T> constructor;
        try {
            constructor = instanceClazz.getDeclaredConstructor(beanStructure.getClazz());
        } catch (Exception e) {
            throw new FastormException("创建LastOperateFeature实例出错", e);
        }

        T obj;
        try {
            obj = constructor.newInstance(t);
        } catch (Exception e) {
            throw new FastormException("创建LastOperateFeature实例出错", e);
        }

        return obj;
    }
}
