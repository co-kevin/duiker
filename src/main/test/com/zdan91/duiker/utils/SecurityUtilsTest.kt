package com.zdan91.duiker.utils

import com.zdan91.duiker.bean.User
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class SecurityUtilsTest {

    @Test
    @BeforeEach
    fun putCurrentLoginUser() {
        SecurityUtils.putCurrentLoginUser(User(-1, "15000000000"))
    }

    @Test
    fun currentLoginUser() {
        val user = SecurityUtils.currentLoginUser()
        println(user)
    }

    @Test
    fun isAuthenticated() {
        println(SecurityUtils.isAuthenticated())
    }
}
