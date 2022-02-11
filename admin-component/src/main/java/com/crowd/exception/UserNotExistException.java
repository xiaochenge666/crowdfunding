package com.crowd.exception;

public class UserNotExistException extends RuntimeException {


    public UserNotExistException() {
        super("错误操作！当前用户不存在!");
    }

    public UserNotExistException(String message) {
        super(message);
    }
}
