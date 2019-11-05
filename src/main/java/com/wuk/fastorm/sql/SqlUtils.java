package com.wuk.fastorm.sql;

import java.util.ArrayList;
import java.util.List;

public class SqlUtils {

    private SqlUtils() {

    }

    /**
     * 获取变量信息，需控制变量的顺序
     * @param parts
     * @return
     */
    public static List<SqlParam> getParams(Sql... parts) {
        List<SqlParam> params = new ArrayList<>();
        for (Sql part : parts) {
            params.addAll(part.getParams());
        }
        return params;
    }

    /**
     * 获取变量信息，需控制变量的顺序
     * @param p
     * @param parts
     * @return
     */
    public static List<SqlParam> getParamsAndSingle(Sql p, Sql... parts) {
        List<SqlParam> params = new ArrayList<>(p.getParams());
        for (Sql part : parts) {
            params.addAll(part.getParams());
        }
        return params;
    }
}
