package com.zdan91.duiker.exception;

/**
 * 断言工具类
 */
public class Assert {

    private static final int DEFAULT_CODE = 400;

    /**
     * 表达式返回 true 则抛出异常
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message, int code) {
        if (expression) {
            throw new CustomException(message, code);
        }
    }

    /**
     * 表达式返回 true 则抛出异常
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (expression) {
            throw new CustomException(message, DEFAULT_CODE);
        }
    }

    /**
     * 如果该表达式返回 false 抛出异常
     *
     * @param expression
     * @param message
     */
    public static void isFalse(boolean expression, String message) {
        isTrue(!expression, message, DEFAULT_CODE);
    }

    /**
     * 如果该表达式返回 false 抛出异常,自定义 code
     *
     * @param expression
     * @param message
     */
    public static void isFalse(boolean expression, String message, int code) {
        isTrue(!expression, message, code);
    }
}
