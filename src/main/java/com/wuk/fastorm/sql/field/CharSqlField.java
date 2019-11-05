package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlParam;

import java.util.Collections;
import java.util.List;

/**
 * 普通字符
 */
public class CharSqlField extends AbstractSqlField {

    private CharSequence charSequence;

    public CharSqlField(CharSequence charSequence) {
        this(charSequence, null);
    }

    public CharSqlField(CharSequence charSequence, String aliasName) {
        super(aliasName);
        this.charSequence = charSequence;
    }

    @Override
    public String toSql() {
        return String.format("'%s'", charSequence);
    }

    @Override
    public List<SqlParam> getParams() {
        return Collections.emptyList();
    }
}
