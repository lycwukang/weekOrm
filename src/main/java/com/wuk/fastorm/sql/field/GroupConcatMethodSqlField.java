package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * group_concat(x)
 */
public class GroupConcatMethodSqlField extends AbstractSqlField {

    private SqlField field;

    public GroupConcatMethodSqlField(String name) {
        this(new NameSqlField(name), null);
    }

    public GroupConcatMethodSqlField(String name, String aliasName) {
        this(new NameSqlField(name), aliasName);
    }

    public GroupConcatMethodSqlField(SqlField field) {
        this(field, null);
    }

    public GroupConcatMethodSqlField(SqlField field, String aliasName) {
        super(aliasName);
        this.field = field;
    }

    @Override
    public String toSql() {
        return String.format("GROUP_CONCAT(%s)", field.getSql());
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParams(field);
    }
}
