package com.zdan91.duiker.exception;

import org.springframework.http.HttpStatus;


public class CustomException extends RuntimeException {
    private String message;
    private int code;

    public CustomException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message) {
        this.message = message;
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
