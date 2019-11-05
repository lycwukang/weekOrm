package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.lang.StringUtils;
import com.wuk.fastorm.sql.SqlField;

public abstract class AbstractSqlField implements SqlField {

    private String aliasName;

    public AbstractSqlField(String aliasName) {
        this.aliasName = aliasName;
    }

    public abstract String toSql();

    @Override
    public String getSql() {
        return StringUtils.isEmpty(aliasName) ? toSql() : String.format("%s AS `%s`", toSql(), aliasName);
    }

    @Override
    public String getAliasName() {
        return aliasName;
    }
}
