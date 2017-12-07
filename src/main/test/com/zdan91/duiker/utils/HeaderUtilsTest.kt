package com.zdan91.duiker.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HeaderUtilsTest {

    @Test
    fun createDialog() {
        val httpHeaders = HeaderUtils.createDialog("warning", "用户名已存在")
        print(httpHeaders)
    }
}
