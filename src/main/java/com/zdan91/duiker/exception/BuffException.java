package com.zdan91.duiker.exception;

import org.springframework.http.HttpStatus;

public class BuffException extends RuntimeException {
    private String message;
    private int code;

    public BuffException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public BuffException(String message) {
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
        return "BuffException{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
