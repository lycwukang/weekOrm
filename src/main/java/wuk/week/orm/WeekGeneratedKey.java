package wuk.week.orm;

public class WeekGeneratedKey {

    private Object key;

    public void setKey(Object key) {
        this.key = key;
    }

    public int getInt() {
        return Integer.parseInt(key.toString());
    }

    public long getLong() {
        return Long.parseLong(key.toString());
    }
}