package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;
import com.wuk.fastorm.sql.SqlParam;
import com.wuk.fastorm.sql.SqlUtils;

import java.util.List;

/**
 * concat_ws(field, x, y, z...)
 */
public class ConcatWsMethodSqlField extends AbstractSqlField {

    private SqlField field;
    private SqlField[] strList;

    public ConcatWsMethodSqlField(String name, SqlField... strList) {
        this(new NameSqlField(name), null, strList);
    }

    public ConcatWsMethodSqlField(String name, String aliasName, SqlField... strList) {
        this(new NameSqlField(name), aliasName, strList);
    }

    public ConcatWsMethodSqlField(SqlField field, SqlField... strList) {
        this(field, null, strList);
    }

    public ConcatWsMethodSqlField(SqlField field, String aliasName, SqlField... strList) {
        super(aliasName);
        this.field = field;
        this.strList = strList;
    }

    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strList.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(strList[i].getSql());
        }
        return String.format("CONCAT_WS(%s, %s)", field.getSql(), builder);
    }

    @Override
    public List<SqlParam> getParams() {
        return SqlUtils.getParamsAndSingle(field, strList);
    }
}
