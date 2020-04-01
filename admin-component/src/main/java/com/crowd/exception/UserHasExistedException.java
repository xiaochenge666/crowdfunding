package com.crowd.exception;

public class UserHasExistedException extends RuntimeException{
    public UserHasExistedException() {
        super();
    }

    public UserHasExistedException(String message) {
        super(message);
    }

    public UserHasExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserHasExistedException(Throwable cause) {
        super(cause);
    }
}
