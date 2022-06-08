package exception;

public class NotExpiredTermException extends RuntimeException {
    public NotExpiredTermException(String message) {
        super(message);
    }
}
