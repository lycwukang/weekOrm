package wuk.week.orm;

public enum JoinType {

    and,
    or;

    JoinType() {
    }

    public static String sql(JoinType type) {
        switch (type) {
            case and:
                return "and";
            case or:
                return "or";
        }
        return "";
    }
}