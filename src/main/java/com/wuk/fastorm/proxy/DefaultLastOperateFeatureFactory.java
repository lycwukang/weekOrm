package com.wuk.fastorm.proxy;

import com.wuk.fastorm.bean.BeanStructure;
import com.wuk.fastorm.exception.FastormException;
import com.wuk.fastorm.javassist.BeanClassBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的LastOperateFeatureFactory实现
 */
public class DefaultLastOperateFeatureFactory implements LastOperateFeatureFactory {

    /**
     * 缓存已经创建的class对象
     */
    private static final Map<Class<?>, Class<?>> INSTANCE_CLASS_MAP = new ConcurrentHashMap<>(12);

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
            INSTANCE_CLASS_MAP.putIfAbsent(beanStructure.getClazz(), instanceClass(beanStructure));
        }
        Class<T> instanceClazz = (Class<T>) INSTANCE_CLASS_MAP.get(beanStructure.getClazz());

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

    @SuppressWarnings("unchecked")
    public <T> Class<T> instanceClass(BeanStructure<T> beanStructure) {
        String proxyPackageName = String.format("%s.%s", DefaultLastOperateFeatureFactory.class.getPackage().getName(), "factory");
        BeanClassBuilder classBuilder = BeanClassBuilder
                .instance(String.format("%s.%s%s", proxyPackageName, beanStructure.getClazz().getSimpleName(), "$LastOperateFeatureProxy"), Modifier.PUBLIC)
                .setSuperClass(beanStructure.getClazz())
                .addInterfaceClass(LastOperateFeature.class)
                .addField("lastOperateFieldName", String.class, Modifier.PRIVATE)
                .addMethod("findLastOperateFieldName", String.class, new Class[0], Modifier.PUBLIC, "{return $0.lastOperateFieldName;}")
                .addField("obj", beanStructure.getClazz(), Modifier.PRIVATE)
                .addConstructor(new Class[]{beanStructure.getClazz()}, Modifier.PUBLIC, "{$0.obj = $1;}");

        for (String fieldName : beanStructure.getFieldNames()) {
            Method readMethod = beanStructure.getReadMethodMap().get(fieldName);
            classBuilder.addMethod(readMethod.getName(), readMethod.getReturnType(), readMethod.getParameterTypes(), readMethod.getModifiers(), String.format("{$0.lastOperateFieldName = \"%s\"; return $0.obj.%s();}", fieldName, readMethod.getName()));
            Method writeMethod = beanStructure.getWriteMethodMap().get(fieldName);
            classBuilder.addMethod(writeMethod.getName(), writeMethod.getReturnType(), writeMethod.getParameterTypes(), writeMethod.getModifiers(), String.format("{$0.lastOperateFieldName = \"%s\"; $0.obj.%s($1);}", fieldName, writeMethod.getName()));
        }

        return classBuilder.create();
    }
}
