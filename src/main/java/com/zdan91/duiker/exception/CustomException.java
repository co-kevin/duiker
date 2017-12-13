package com.zdan91.duiker.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义业务异常
 */
public class CustomException extends RuntimeException {
    private String message;//错误信息
    private int code;//错误码

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
