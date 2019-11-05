package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;

/**
 * field + 1
 */
public class IncreaseSqlField extends AddSqlField {

    public IncreaseSqlField(String name) {
        super(name, new NumberSqlField(1));
    }

    public IncreaseSqlField(String name, String aliasName) {
        super(name, new NumberSqlField(1), aliasName);
    }

    public IncreaseSqlField(SqlField field) {
        super(field, new NumberSqlField(1));
    }

    public IncreaseSqlField(SqlField field, String aliasName) {
        super(field, new NumberSqlField(1), aliasName);
    }
}
