package com.wuk.fastorm.sql.collection;

import com.wuk.fastorm.sql.SqlField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlFieldCollection extends ArrayList<SqlField> {

    public SqlFieldCollection() {

    }

    public SqlFieldCollection(SqlField... fields) {
        Collections.addAll(this, fields);
    }

    public SqlFieldCollection(List<SqlField> fields) {
        addAll(fields);
    }
}
