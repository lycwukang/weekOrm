package wuk.week.orm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeekManager {

    // 当前所有的数据源
    private List<WeekSource> dataSources = new ArrayList<>();

    public <T> ExecuteBuilder<T> build(String name, Class<T> clazz) {
        return build(name, null, clazz);
    }

    public <T> ExecuteBuilder<T> build(String name, WeekTransaction transaction, Class<T> clazz) {
        DataSource dataSource = findDataSource(name);
        if (dataSource == null) {
            throw new RuntimeException("找不到数据源[name=" + name + "]");
        }

        if (transaction != null && !name.equals(transaction.getName())) {
            throw new RuntimeException("当前数据源与事务数据源不一致[name=" + name + ", transaction=" + transaction.getName() + "]");
        }

        return new ExecuteBuilder<>(dataSource, transaction, clazz);
    }

    public WeekTransaction begin(String name) {
        return begin(name, Connection.TRANSACTION_READ_COMMITTED, true);
    }

    public WeekTransaction begin(String name, int transactionIsolation) {
        return begin(name, transactionIsolation, true);
    }

    public WeekTransaction begin(String name, boolean autoRollback) {
        return begin(name, Connection.TRANSACTION_READ_COMMITTED, autoRollback);
    }

    public WeekTransaction begin(String name, int transactionIsolation, boolean autoRollback) {
        DataSource dataSource = findDataSource(name);
        if (dataSource == null) {
            throw new RuntimeException("找不到数据源[name=" + name + "]");
        }

        WeekTransaction transaction;
        try {
            transaction = new WeekTransaction(name, dataSource, transactionIsolation, autoRollback);
        } catch (SQLException e) {
            throw new RuntimeException("创建事务对象失败[name=" + name + "]");
        }
        return transaction;
    }

    private DataSource findDataSource(String name) {
        for (WeekSource dataSource : dataSources) {
            if (dataSource.getName().equals(name)) {
                return dataSource.getDataSource();
            }
        }
        return null;
    }

    public List<WeekSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<WeekSource> dataSources) {
        this.dataSources = dataSources;
    }
}