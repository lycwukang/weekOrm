package com.wuk.fastorm.sql.impl;

import com.wuk.fastorm.exception.FastormSqlException;
import com.wuk.fastorm.sql.Sql;
import com.wuk.fastorm.sql.SqlParam;

import java.util.*;

public class SimpleSql implements Sql {

    private String sql;
    private String realSql;
    private List<String> paramNames = new ArrayList<>();
    private Map<String, SqlParam> paramMap = new HashMap<>();

    private static final String paramBeginChar = "#{";
    private static final String paramEndChar = "}";

    public SimpleSql(String sql) {
        this.realSql = sql;

        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (sql.indexOf(paramBeginChar, index) >= 0) {
            int beginIndex = sql.indexOf(paramBeginChar, index);

            builder.append(sql.substring(index, beginIndex));

            if (sql.indexOf(paramEndChar, beginIndex) >= 0) {
                int endIndex = sql.indexOf(paramEndChar, beginIndex);

                builder.append("?");
                paramNames.add(sql.substring(beginIndex + paramBeginChar.length(), endIndex));

                index = endIndex + 1;
            }
        }
        builder.append(sql.substring(index));

        this.sql = builder.toString();
    }

    public void addParam(String name, Object obj) {
        paramMap.put(name, new SqlParam(obj.getClass(), obj));
    }

    @Override
    public String getSql() {
        return sql;
    }

    public String getRealSql() {
        return realSql;
    }

    @Override
    public List<SqlParam> getParams() {
        List<SqlParam> params = new ArrayList<>();
        for (String paramName : paramNames) {
            if (!paramMap.containsKey(paramName)) {
                throw new FastormSqlException(String.format("构建sql出错，找不到参数%s", paramName));
            }
            params.add(paramMap.get(paramName));
        }
        return params;
    }
}
