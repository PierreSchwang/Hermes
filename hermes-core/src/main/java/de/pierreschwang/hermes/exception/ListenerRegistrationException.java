package de.pierreschwang.hermes.exception;

public class ListenerRegistrationException extends RuntimeException {

    public ListenerRegistrationException() {
    }

    public ListenerRegistrationException(String message) {
        super(message);
    }

    public ListenerRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListenerRegistrationException(Throwable cause) {
        super(cause);
    }
}
