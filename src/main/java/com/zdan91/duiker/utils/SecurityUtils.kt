package com.zdan91.duiker.utils

import javax.servlet.http.HttpServletRequest

/**
 * 获得当前登录用户信息的安全工具类
 * TODO 实现逻辑
 *
 * @author hookszhang
 */
object SecurityUtils {

    fun getCurrentUserId(request: HttpServletRequest): Int {
        return -1
    }

    fun getCurrentUserPhoneNumber(request: HttpServletRequest): String? {
        return null
    }

    fun getCurrentUserName(request: HttpServletRequest): String? {
        return null
    }
}
