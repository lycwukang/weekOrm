package wuk.week.orm;

public class HavingColumn {

    private Column column;
    private OperatorType operatorType;
    private Object value;

    public HavingColumn(Column column, OperatorType operatorType, Object value) {
        this.column = column;
        this.operatorType = operatorType;
        this.value = value;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}