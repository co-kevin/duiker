package com.zdan91.duiker.utils;

import com.xiaoleilu.hutool.util.StrUtil;
import com.zdan91.duiker.exception.CustomException;

/**
 * 断言工具类
 */
public class AssertUtil {

    private static final int DEFAULT_CODE = 400;
    private static final String RESOURCE_NOT_FOUND = "resource not found";
    private static final String STRING_NAME = "java.lang.String";
    private static final String DEFAULT_MESSAGE = "对象不能为空";

    public static void main(String args[]) {
        canNotBeNull(3);
        canNotBeNull("");
    }

    /**
     * 对象不能为空
     *
     * @param object
     */
    public static void canNotBeNull(Object object) {
        canNotBeNull(object, DEFAULT_MESSAGE);
    }

    /**
     * 对象不能为空
     * 如果是字符串,也可判断.
     *
     * @param object
     * @param message
     */
    public static void canNotBeNull(Object object, String message) {
        isTrue(object == null, message);
        if (object.getClass().getName().equals(STRING_NAME)) {
            isTrue(StrUtil.isEmpty(object.toString()), message);
        }
    }

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
     * 如果对象为空则抛出资源未找到的404异常
     *
     * @param object
     */
    public static void notFound(Object object) {
        isTrue(object == null, RESOURCE_NOT_FOUND, 404);
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
