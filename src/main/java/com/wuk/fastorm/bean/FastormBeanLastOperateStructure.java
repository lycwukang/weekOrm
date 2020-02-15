package com.wuk.fastorm.bean;

import com.wuk.fastorm.proxy.DefaultLastOperateFeatureFactory;
import com.wuk.fastorm.proxy.LastOperateFeature;

import java.util.function.Function;

public class FastormBeanLastOperateStructure<T> extends FastormBeanStructure<T> {

    private T instance;
    private LastOperateFeature instanceLastOperateFeature;

    public FastormBeanLastOperateStructure(FastormBeanStructure<T> beanStructure) {
        super(beanStructure);
        setTable(beanStructure.getTable());
        setColumnMap(beanStructure.getColumnMap());

        instance = new DefaultLastOperateFeatureFactory().instance(beanStructure);
        instanceLastOperateFeature = (LastOperateFeature) instance;
    }

    /**
     * 获取列名称
     * @param function
     * @return
     */
    public String findFieldName(Function<T, ?> function) {
        function.apply(instance);

        return instanceLastOperateFeature.findLastOperateFieldName();
    }

    /**
     * 获取列名称
     * @param function
     * @return
     */
    public String findColumnName(Function<T, ?> function) {
        return findColumnName(findFieldName(function));
    }
}
