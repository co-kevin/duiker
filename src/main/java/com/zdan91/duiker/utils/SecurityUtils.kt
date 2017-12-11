package com.zdan91.duiker.utils

import com.zdan91.duiker.bean.User

/**
 * Spring Security JWT Utils.
 *
 * @author hookszhang
 */
object SecurityUtils {

    private val threadLocal = ThreadLocal<User>()

    /**
     * 从 ThreadLocal 中获得当前登录用户
     */
    fun currentLoginUser() : User? {
        return threadLocal.get()
    }

    /**
     * 将当前登录用户放入 ThreadLocal 中
     */
    fun putCurrentLoginUser(user: User) {
        threadLocal.set(user)
    }

    /**
     * Check if a user is authenticated.
     */
    fun isAuthenticated() : Boolean {
        return threadLocal.get() != null
    }
}
