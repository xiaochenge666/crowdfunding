package com.crowd.exception;

public class RecordHasExistedException extends RuntimeException {

    public RecordHasExistedException() {
        super("该记录已存在，请重试！");
    }

    public RecordHasExistedException(String message) {
        super(message);
    }
}
