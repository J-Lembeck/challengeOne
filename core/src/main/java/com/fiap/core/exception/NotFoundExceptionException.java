package com.fiap.core.exception;

public class NotFoundExceptionException extends Exception {

    private String code;

    public NotFoundExceptionException(String message, String code) {
        super(message);
        this.code = code;
    }
}
