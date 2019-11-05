package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * concat(field, val0, val1...)
 */
public class ConcatMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private int index;
    private SqlField[] strList;

    public ConcatMethodSqlField(String name, int index, SqlField... strList) {
        this(new NameSqlField(name), index, null, strList);
    }

    public ConcatMethodSqlField(String name, int index, String aliasName, SqlField... strList) {
        this(new NameSqlField(name), index, aliasName, strList);
    }

    public ConcatMethodSqlField(SqlField field, int index, SqlField... strList) {
        this(field, index, null, strList);
    }

    private ConcatMethodSqlField(SqlField field, int index, String aliasName, SqlField... strList) {
        super(aliasName);
        this.field = field;
        this.index = index;
        this.strList = strList;
    }

    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strList.length + 1; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            if (i == index) {
                builder.append(field.getSql());
            } else {
                builder.append(strList[i < index ? i : i - 1].getSql());
            }
        }

        return String.format("CONCAT(%s)", builder);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParamsAndSingle(field, strList);
    }
}
