package com.wuk.fastorm.bean;

import com.wuk.fastorm.exception.FastormBeanAnalyzeException;
import com.wuk.fastorm.lang.ArrayUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的bean解析器实现
 */
public class DefaultBeanAnalyze implements BeanAnalyze {

    /**
     * 缓存解析结果
     */
    private static final Map<Class<?>, BeanStructure> ANALYZE_MAP = new ConcurrentHashMap<>(12);

    /**
     * 允许bean中字段定义的类型，不在此列表中的字段不支持进行操作
     */
    public static final Class<?>[] ANALYZE_ENABLED_TYPES = {
            int.class, long.class, float.class, double.class, boolean.class,
            Integer.class, Long.class, Float.class, Double.class, Boolean.class,
            String.class, BigDecimal.class, Date.class
    };

    /**
     * 忽略bean的字段，class是Object的getClass解析出的字段
     */
    public static final String[] PASS_FIELD_NAMES = {"class"};

    @SuppressWarnings("unchecked")
    @Override
    public <T> BeanStructure<T> analyze(Class<T> clazz) {
        if (!ANALYZE_MAP.containsKey(clazz)) {
            ANALYZE_MAP.putIfAbsent(clazz, analyzeBean(clazz));
        }
        return ANALYZE_MAP.get(clazz);
    }

    private <T> BeanStructure<T> analyzeBean(Class<T> clazz) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new FastormBeanAnalyzeException(clazz, "解析bean出错，请检查bean是否满足JavaBean规范，详细内容请访问 https://zh.wikipedia.org/zh-hans/JavaBeans");
        }

        BeanStructure<T> beanStructure = new BeanStructure<>();
        beanStructure.setClazz(clazz);
        beanStructure.setFieldNames(new ArrayList<>(6));
        beanStructure.setFieldMap(new HashMap<>(6));
        beanStructure.setReadMethodMap(new HashMap<>(6));
        beanStructure.setWriteMethodMap(new HashMap<>(6));

        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            if (!ArrayUtils.contains(PASS_FIELD_NAMES, descriptor.getName())) {
                if (!ArrayUtils.contains(ANALYZE_ENABLED_TYPES, descriptor.getPropertyType())) {
                    throw new FastormBeanAnalyzeException(clazz, "解析bean出错，字段不支持除基本类型之外的其他类型，支持的基本类型有[int(Integer), long(Long), float(Float), double(Double), boolean(Boolean), String, BigDecimal, Date]");
                }

                if (descriptor.getReadMethod() == null) {
                    throw new FastormBeanAnalyzeException(clazz, "解析bean出错，readMethod为空");
                }

                if (descriptor.getWriteMethod() == null) {
                    throw new FastormBeanAnalyzeException(clazz, "解析bean出错，writeMethod为空");
                }

                Field field;
                try {
                    field = clazz.getDeclaredField(descriptor.getName());
                } catch (Exception e) {
                    throw new FastormBeanAnalyzeException(clazz, "解析bean出错，尝试获取字段的名称为" + descriptor.getName(), e);
                }

                beanStructure.getFieldNames().add(descriptor.getName());
                beanStructure.getFieldMap().put(descriptor.getName(), field);
                beanStructure.getReadMethodMap().put(descriptor.getName(), descriptor.getReadMethod());
                beanStructure.getWriteMethodMap().put(descriptor.getName(), descriptor.getWriteMethod());
            }
        }

        if (beanStructure.getFieldNames().isEmpty()) {
            throw new FastormBeanAnalyzeException(clazz, "解析bean出错，无法解析出任何信息");
        }

        return beanStructure;
    }
}
