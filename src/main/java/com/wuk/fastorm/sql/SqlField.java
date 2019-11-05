package com.wuk.fastorm.sql;

/**
 * sql字段，可能是单个字段名称，也有可能是方法
 */
public interface SqlField extends Sql {

    /**
     * 字段别名
     * @return
     */
    String getAliasName();
}
