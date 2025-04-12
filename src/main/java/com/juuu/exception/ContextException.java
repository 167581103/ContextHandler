package com.juuu.exception;

public class ContextException extends RuntimeException {
    private Integer errorCode;

    private String msg;

    public ContextException(Integer errorCode, String msg){
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public ContextException(String msg){
        super(msg);
        this.msg = msg;
    }

}
