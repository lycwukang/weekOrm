package wuk.week.orm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeekManager {

    /**
     * 当前所有的数据源
     */
    private List<WeekSource> dataSources = new ArrayList<>();

    public <T> ExecuteBuilder<T> build(Class<T> clazz) {
        return build(dataSources.get(0).getName(), null, clazz);
    }

    public <T> ExecuteBuilder<T> build(WeekTransaction transaction, Class<T> clazz) {
        return build(dataSources.get(0).getName(), transaction, clazz);
    }

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

    public WeekTransaction begin() {
        return begin(dataSources.get(0).getName());
    }

    public WeekTransaction begin(int transactionIsolation) {
        return begin(dataSources.get(0).getName(), transactionIsolation);
    }

    public WeekTransaction begin(String name) {
        return begin(name, Connection.TRANSACTION_READ_COMMITTED);
    }

    public WeekTransaction begin(String name, int transactionIsolation) {
        DataSource dataSource = findDataSource(name);
        if (dataSource == null) {
            throw new RuntimeException("找不到数据源[name=" + name + "]");
        }

        WeekTransaction transaction;
        try {
            transaction = new WeekTransaction(name, dataSource, transactionIsolation);
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