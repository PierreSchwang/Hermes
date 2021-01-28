package de.pierreschwang.hermes.exception;

public class IllegalServiceException extends RuntimeException {

    public IllegalServiceException() {
        super();
    }

    public IllegalServiceException(String message) {
        super(message);
    }

    public IllegalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalServiceException(Throwable cause) {
        super(cause);
    }

}
