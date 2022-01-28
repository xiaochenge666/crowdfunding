package com.crowd.utils;

public class ResponseEntity<T>{

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";
    private static final String NO_MESSAGE = "NO_MESSAGE";
    private static final String NO_DATA = "NO_DATA";


    private String operationResult;
    private String operationMessage;
    private T queryData;

    private ResponseEntity(String operationResult, String operationMessage, T queryData) {
        this.operationResult = operationResult;
        this.operationMessage = operationMessage;
        this.queryData = queryData;
    }

    public static <type> ResponseEntity<type> successWithoutData(){
        return new ResponseEntity<type>(SUCCESS,NO_MESSAGE,null);
    }

    public static <type> ResponseEntity<type> successWithData(type data){
        return new ResponseEntity<type>(SUCCESS,NO_MESSAGE,data);
    }
    public static <type> ResponseEntity<type> fail(String msg){
        return new ResponseEntity<type>(FAILED,msg,null);
    }

    public static <type> ResponseEntity<type> fail(String msg,type data){
        return new ResponseEntity<type>(FAILED,msg,data);
    }
    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public T getQueryData() {
        return queryData;
    }

    public void setQueryData(T queryData) {
        this.queryData = queryData;
    }
}