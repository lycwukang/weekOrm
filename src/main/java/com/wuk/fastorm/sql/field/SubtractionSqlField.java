package com.wuk.fastorm.sql.field;

import com.wuk.fastorm.sql.SqlField;

/**
 * field - 1
 */
public class SubtractionSqlField extends SubtractSqlField {

    public SubtractionSqlField(String name) {
        super(new NameSqlField(name), new NumberSqlField(1), null);
    }

    public SubtractionSqlField(String name, String aliasName) {
        super(new NameSqlField(name), new NumberSqlField(1), aliasName);
    }

    public SubtractionSqlField(SqlField field) {
        super(field, new NumberSqlField(1));
    }

    public SubtractionSqlField(SqlField field, String aliasName) {
        super(field, new NumberSqlField(1), aliasName);
    }
}
