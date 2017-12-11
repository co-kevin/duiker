package com.zdan91.duiker.config

import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.RuntimeException

/**
 * Configuration feign error decoder.
 *
 * @author hookszhang
 */
@Configuration
open class FeignConfiguration {

    private val log = LoggerFactory.getLogger(FeignConfiguration::class.java)

    /**
     * Duiker Error Decoder.
     *
     * Configure application.yml : <b>duiker.feign.error-decoder.enable</b> to enable this error decoder.
     * Decoder Exception from rpc service.
     */
    @Bean
    @ConditionalOnProperty(value = ["duiker.feign.error-decoder.enable"], havingValue = "ok")
    open fun duikerErrorDecoder(): ErrorDecoder {
        log.debug("Register Duiker Error Decoder")
        return ErrorDecoder { methodKey, _ ->
            log.error("Decode error: {}", methodKey)
            RuntimeException(methodKey)
        }
    }
}
