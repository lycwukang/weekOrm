package com.wuk.fastorm.sql;

import java.util.List;

public abstract class AbstractSql {

    /**
     * 获取变量信息，需控制变量的顺序
     * @param parts
     * @return
     */
    protected List<SqlParam> getParams(Sql... parts) {
        return SqlUtils.getParams(parts);
    }

    /**
     * 获取变量信息，需控制变量的顺序
     * @param p
     * @param parts
     * @return
     */
    protected List<SqlParam> getParamsAndSingle(Sql p, Sql... parts) {
        return SqlUtils.getParamsAndSingle(p, parts);
    }
}
