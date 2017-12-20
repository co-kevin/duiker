package com.zdan91.duiker.exception;

public class Assert {

    private static final int DEFAULT_CODE = 400;

    public static void isTrue(boolean expression, String message, int code) {
        if (expression) {
            throw new CustomException(message, code);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (expression) {
            throw new CustomException(message, DEFAULT_CODE);
        }
    }

    public static void notFound(Object object) {
        isTrue(object == null, "resource not found", 404);
    }

    public static void isFalse(boolean expression, String message) {
        isTrue(!expression, message, DEFAULT_CODE);
    }


    public static void isFalse(boolean expression, String message, int code) {
        isTrue(!expression, message, code);
    }
}
