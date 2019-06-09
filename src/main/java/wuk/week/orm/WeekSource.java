package wuk.week.orm;

import javax.sql.DataSource;

public class WeekSource {

    /**
     * 数据库简称
     */
    private String name;
    /**
     * 数据源
     */
    private DataSource dataSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}