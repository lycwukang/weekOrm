package com.wuk.fastorm.bean;

import com.wuk.fastorm.annontation.FastormColumn;
import com.wuk.fastorm.annontation.FastormTable;
import com.wuk.fastorm.exception.FastormBeanAnalyzeException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的FastormBeanAnalyze解析器
 */
public class DefaultFastormBeanAnalyze implements FastormBeanAnalyze {

    /**
     * 缓存解析结果
     */
    private static final Map<Class<?>, FastormBeanStructure<?>> ANALYZE_MAP = new ConcurrentHashMap<>(12);

    @Override
    public <T> FastormBeanLastOperateStructure<T> analyze(Class<T> clazz) {
        return analyze(new DefaultBeanAnalyze().analyze(clazz));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> FastormBeanLastOperateStructure<T> analyze(BeanStructure<T> beanStructure) {
        Class<?> clazz = beanStructure.getClazz();

        if (!ANALYZE_MAP.containsKey(clazz)) {
            ANALYZE_MAP.putIfAbsent(clazz, analyzeBean(beanStructure));
        }
        return new FastormBeanLastOperateStructure<>((FastormBeanStructure<T>) ANALYZE_MAP.get(clazz));
    }

    private <T> FastormBeanStructure<T> analyzeBean(BeanStructure<T> beanStructure) {
        Class<?> clazz = beanStructure.getClazz();

        FastormTable fastormTable = clazz.getAnnotation(FastormTable.class);
        if (fastormTable == null) {
            throw new FastormBeanAnalyzeException(clazz, "bean没有在class上添加@FastormTable注解");
        }

        FastormBeanStructure<T> fastormBeanStructure = new FastormBeanStructure<>(beanStructure);
        fastormBeanStructure.setTable(fastormTable);
        fastormBeanStructure.setColumnMap(new HashMap<>());

        for (String name : beanStructure.getFieldNames()) {
            FastormColumn fastormColumn = beanStructure.getFieldMap().get(name).getAnnotation(FastormColumn.class);
            if (fastormColumn == null) {
                throw new FastormBeanAnalyzeException(clazz, "bean没有在field上添加@FastormColumn注解，尝试获取字段的名称为" + name);
            }

            fastormBeanStructure.getColumnMap().put(name, fastormColumn);
        }

        return fastormBeanStructure;
    }
}
