package com.crowd.exception;

public class AddAdminException extends RuntimeException {
    public AddAdminException() {
        super();
    }

    public AddAdminException(String message) {
        super(message);
    }

    public AddAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddAdminException(Throwable cause) {
        super(cause);
    }
}
