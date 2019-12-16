package com.wuk.fastorm.sql.collection;

import com.wuk.fastorm.sql.SqlField;

import java.util.ArrayList;
import java.util.Collections;

public class SqlFieldCollection extends ArrayList<SqlField> {

    public SqlFieldCollection() {
        this(6);
    }

    public SqlFieldCollection(int size) {
        super(size);
    }

    public static SqlFieldCollection newInstance(SqlField... fields) {
        SqlFieldCollection fieldCollection = new SqlFieldCollection();
        Collections.addAll(fieldCollection, fields);
        return fieldCollection;
    }
}
