package com.wuk.fastorm.sql;

import java.util.List;

public interface Sql {

    /**
     * 生成sql语句
     * @return
     */
    String getSql();

    /**
     * 获取sql字段中的变量部分
     * @return
     */
    List<SqlParam> getParams();
}
