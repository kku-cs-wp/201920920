package kr.ac.kku.cs.wp.JW_P1.tools.message;

public class MessageException extends RuntimeException {

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
