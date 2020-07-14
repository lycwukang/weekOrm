package com.wuk.fastorm.javassist;

import com.wuk.fastorm.exception.FastormJavassistException;
import javassist.*;

public class BeanClassBuilder {

    /**
     * 对象池
     */
    private static final ClassPool classPool = ClassPool.getDefault();

    static {
        classPool.insertClassPath(new ClassClassPath(BeanClassBuilder.class));
    }

    private CtClass ctClass;

    public static BeanClassBuilder instance(String name, int modifiers) {
        BeanClassBuilder beanClassBuilder = new BeanClassBuilder();
        beanClassBuilder.ctClass = classPool.makeClass(name);
        beanClassBuilder.ctClass.setModifiers(modifiers);
        return beanClassBuilder;
    }

    public BeanClassBuilder setSuperClass(Class<?> clazz) {
        CtClass superCtClass = findCtClass(clazz);

        try {
            ctClass.setSuperclass(superCtClass);
        } catch (Exception e) {
            throw new FastormJavassistException(ctClass, "设置父类出错，className=" + clazz.getName(), e);
        }

        return this;
    }

    public BeanClassBuilder addInterfaceClass(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new FastormJavassistException(ctClass, "不是接口类型，className=" + clazz.getName());
        }
        ctClass.addInterface(findCtClass(clazz));

        return this;
    }

    public BeanClassBuilder addField(String name, Class<?> clazz, int modifiers) {
        CtClass fieldCtClass = findCtClass(clazz);

        try {
            CtField ctField = new CtField(fieldCtClass, name, ctClass);
            ctField.setModifiers(modifiers);
            ctClass.addField(ctField);
        } catch (Exception e) {
            throw new FastormJavassistException(ctClass, String.format("添加字段出错，fieldName=%s，className=%s", name, clazz.getName()), e);
        }

        return this;
    }

    public BeanClassBuilder addMethod(String name, Class<?> clazz, Class<?>[] paramsClazz, int modifiers, String body) {
        CtClass methodCtClass = findCtClass(clazz);

        CtClass[] methodParamsClazz = new CtClass[paramsClazz.length];
        for (int i = 0; i < paramsClazz.length; i++) {
            methodParamsClazz[i] = findCtClass(paramsClazz[i]);
        }

        try {
            CtMethod ctMethod = new CtMethod(methodCtClass, name, methodParamsClazz, ctClass);
            ctMethod.setModifiers(modifiers);
            ctMethod.setBody(body);
            ctClass.addMethod(ctMethod);
        } catch (Exception e) {
            throw new FastormJavassistException(ctClass, String.format("添加方法出错，methodName=%s，className=%s", name, clazz.getName()), e);
        }

        return this;
    }

    public BeanClassBuilder addConstructor(Class<?>[] paramsClazz, int modifiers, String body) {
        CtClass[] constructorParamsClazz = new CtClass[paramsClazz.length];
        for (int i = 0; i < paramsClazz.length; i++) {
            constructorParamsClazz[i] = findCtClass(paramsClazz[i]);
        }

        try {
            CtConstructor ctConstructor = new CtConstructor(constructorParamsClazz, ctClass);
            ctConstructor.setModifiers(modifiers);
            ctConstructor.setBody(body);
            ctClass.addConstructor(ctConstructor);
        } catch (Exception e) {
            throw new FastormJavassistException(ctClass, "创建构造函数出错", e);
        }

        return this;
    }

    public Class create(Class<?> clazz) {
        try {
            return ctClass.toClass(clazz.getClassLoader(), null);
        } catch (Exception e) {
            throw new FastormJavassistException(ctClass, "生成class出错", e);
        }
    }

    private CtClass findCtClass(Class<?> clazz) {
        try {
            return classPool.get(clazz.getName());
        } catch (Exception e) {
            throw new FastormJavassistException(ctClass, "查询class出错，className=" + clazz.getName(), e);
        }
    }
}
