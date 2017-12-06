package com.zdan91.duiker.utils;

/**
 * @author hookszhang
 */
public class SecurityUtils {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    /**
     * 获得当前登录人 userId
     *
     * @return 如果未登录，返回 -1
     */
    public static int getCurrentUserId() {
        return threadLocal.get();
    }

    public static void setCurrentUserId(int userId) {
        threadLocal.set(userId);
    }

    public static void unset() {
        threadLocal.remove();
    }
}
