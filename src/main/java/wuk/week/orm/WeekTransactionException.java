package wuk.week.orm;

public class WeekTransactionException extends RuntimeException {

    public WeekTransactionException() {
    }

    public WeekTransactionException(String message) {
        super(message);
    }

    public WeekTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeekTransactionException(Throwable cause) {
        super(cause);
    }

    public WeekTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
