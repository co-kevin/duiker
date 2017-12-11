package com.zdan91.duiker.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties specific to Duiker.
 *
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "duiker", ignoreUnknownFields = false)
class DuikerProperties {
    val swagger = Swagger()

    class Swagger {
        var scanBasePackage: String? = null
    }
}
