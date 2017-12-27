package com.zdan91.duiker.utils

import org.springframework.http.HttpHeaders

/**
 * Http Header Utils.
 *
 * @author hookszhang
 */
object HeaderUtils {

    /**
     * Create dialog headers
     */
    @JvmStatic
    fun createDialog(type: String, message: String): HttpHeaders? {
        val headers = HttpHeaders()
        headers.set("X-dialog-type", type)
        headers.set("X-dialog-message", toUnicode(message))
        return headers
    }

    private fun toUnicode(message: String): String {
        val unicode = StringBuffer()
        (0 until message.length)
            .map {
                message[it]
            }
            .forEach { unicode.append("\\u" + Integer.toHexString(it.toInt())) }
        return unicode.toString()
    }
}
