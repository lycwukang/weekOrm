package com.wuk.fastorm.proxy;

/**
 * 由于通过lamdba表达式无法获得操作的字段名称，
 * 可以通过继承该接口来获取最后一次操作的字段信息
 */
public interface LastOperateFeature {

    /**
     * 获取最后一次操作的字段名称
     * @return
     */
    String findLastOperateFieldName();
}
