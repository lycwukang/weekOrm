package com.wuk.fastorm.sql.collection;

import com.wuk.fastorm.sql.SqlOrder;

import java.util.ArrayList;
import java.util.Collections;

public class SqlOrderCollection extends ArrayList<SqlOrder> {

    public SqlOrderCollection() {
        this(6);
    }

    public SqlOrderCollection(int size) {
        super(size);
    }

    public static SqlOrderCollection newInstance(SqlOrder... orders) {
        SqlOrderCollection orderCollection = new SqlOrderCollection();
        Collections.addAll(orderCollection, orders);
        return orderCollection;
    }
}
