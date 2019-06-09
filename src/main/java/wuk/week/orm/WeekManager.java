package wuk.week.orm;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class WeekManager {

    private List<WeekSource> dataSources = new ArrayList<>();

    public <T> ExecuteBuilder<T> build(String name, Class<T> clazz) {
        DataSource dataSource = findDataSource(name);
        if (dataSource == null) {
            throw new RuntimeException("找不到数据源[name=" + name + "]");
        }
        return new ExecuteBuilder<>(dataSource, clazz);
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