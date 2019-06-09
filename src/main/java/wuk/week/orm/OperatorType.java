package wuk.week.orm;

public enum OperatorType {

    eq,
    notEq,
    lt,
    ltEq,
    gt,
    gtEq,
    in,
    notIn;

    OperatorType() {
    }

    public static String sql(OperatorType type) {
        switch (type) {
            case eq:
                return "=";
            case notEq:
                return "!=";
            case lt:
                return "<";
            case ltEq:
                return "<=";
            case gt:
                return ">";
            case gtEq:
                return ">=";
            case in:
                return "in";
            case notIn:
                return "not in";
        }
        return "";
    }
}