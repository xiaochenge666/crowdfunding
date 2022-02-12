package com.crowd.exception;

public class ParameterIllegalException extends RuntimeException {
    public ParameterIllegalException() {
        super("参数不合法，请重试！");
    }

    public ParameterIllegalException(String message) {
        super(message);
    }
}
