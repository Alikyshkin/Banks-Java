package exception;

public class NoTransactionsException extends RuntimeException {
    public NoTransactionsException(String message) {
        super(message);
    }
}
