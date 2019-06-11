package wuk.week.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class WeekTransaction {

    private static final Logger logger = LoggerFactory.getLogger(WeekTransaction.class);

    // ======= 数据源名称
    private String name;
    // ======= 数据库链接信息
    private Connection connection;
    // ======= 事务隔离等级
    private int transactionIsolation;

    public WeekTransaction(String name, DataSource dataSource, int transactionIsolation) throws SQLException {
        this.name = name;
        this.connection = dataSource.getConnection();
        this.connection.setAutoCommit(false);
        this.transactionIsolation = transactionIsolation;
        this.connection.setTransactionIsolation(transactionIsolation);
    }

    /**
     * 提交事务
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            if (logger.isErrorEnabled()) {
                logger.error("提交数据出错", e);
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            if (logger.isErrorEnabled()) {
                logger.error("关闭数据库链接出错", e);
            }
        }
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            if (logger.isErrorEnabled()) {
                logger.error("回滚数据出错", e);
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            if (logger.isErrorEnabled()) {
                logger.error("关闭数据库链接出错", e);
            }
        }
    }

    public String getName() {
        return name;
    }

    public Connection getConnection() {
        return connection;
    }

    public int getTransactionIsolation() {
        return transactionIsolation;
    }
}