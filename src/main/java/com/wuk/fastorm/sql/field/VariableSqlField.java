package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * 变量
 */
public class VariableSqlField extends AbstractSqlField {

    private Class<?> clazz;
    private Object obj;

    public VariableSqlField(Class<?> clazz, Object obj) {
        this(clazz, obj, null);
    }

    public VariableSqlField(Class<?> clazz, Object obj, String aliasName) {
        super(aliasName);
        this.clazz = clazz;
        this.obj = obj;
    }

    @Override
    public String toSql() {
        return "?";
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.singletonList(new SqlParam(clazz, obj));
    }
}
