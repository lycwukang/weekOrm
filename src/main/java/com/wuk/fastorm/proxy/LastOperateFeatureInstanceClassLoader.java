package com.wuk.fastorm.proxy;

import com.wuk.fastorm.bean.BeanStructure;
import com.wuk.fastorm.javassist.BeanClassBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class LastOperateFeatureInstanceClassLoader<T> {

    private Class<T> clazz;
    private BeanStructure<T> beanStructure;

    public LastOperateFeatureInstanceClassLoader(BeanStructure<T> beanStructure) {
        this.beanStructure = beanStructure;
    }

    public Class<T> getInstanceClass() {
        if (clazz == null) {
            synchronized (this) {
                if (clazz == null) {
                    clazz = instanceClass();
                }
            }
        }
        return clazz;
    }

    @SuppressWarnings("unchecked")
    public Class<T> instanceClass() {
        String proxyPackageName = String.format("%s.%s", DefaultLastOperateFeatureFactory.class.getPackage().getName(), "factory");
        BeanClassBuilder classBuilder = BeanClassBuilder
                .instance(String.format("%s.%s%s", proxyPackageName, beanStructure.getClazz().getSimpleName(), "$LastOperateFeatureProxy"), Modifier.PUBLIC)
                .setSuperClass(beanStructure.getClazz())
                .addInterfaceClass(LastOperateFeature.class)
                .addField("__lastOperateFieldName", String.class, Modifier.PUBLIC)
                .addMethod("findLastOperateFieldName", String.class, new Class[0], Modifier.PUBLIC, "{return $0.__lastOperateFieldName;}")
                .addField("__obj", beanStructure.getClazz(), Modifier.PUBLIC)
                .addConstructor(new Class[]{beanStructure.getClazz()}, Modifier.PUBLIC, "{$0.__obj = $1;}");

        for (String fieldName : beanStructure.getFieldNames()) {
            Method readMethod = beanStructure.getReadMethodMap().get(fieldName);
            classBuilder.addMethod(readMethod.getName(), readMethod.getReturnType(), readMethod.getParameterTypes(), readMethod.getModifiers(), String.format("{$0.__lastOperateFieldName = \"%s\"; return $0.__obj.%s();}", fieldName, readMethod.getName()));
            Method writeMethod = beanStructure.getWriteMethodMap().get(fieldName);
            classBuilder.addMethod(writeMethod.getName(), writeMethod.getReturnType(), writeMethod.getParameterTypes(), writeMethod.getModifiers(), String.format("{$0.__lastOperateFieldName = \"%s\"; $0.__obj.%s($1);}", fieldName, writeMethod.getName()));
        }

        return classBuilder.create(beanStructure.getClazz());
    }
}
