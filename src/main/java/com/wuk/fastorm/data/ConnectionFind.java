package com.wuk.fastorm.data;

import java.sql.Connection;

public interface ConnectionFind {

    Connection get();

    void setAutoCommit(boolean autoCommit);

    boolean getAutoCommit();

    void close();

    void rollback();

    void commit();
}
