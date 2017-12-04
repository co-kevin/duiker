package com.zdan91.duiker.config

import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
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
     * Configure spring profiles includes: <b>duiker-feign-error-decoder</b> to enable this error decoder.
     * Decoder Exception from rpc service.
     */
    @Bean
    @Profile(Constants.DUIKER_FEIGN_ERROR_DECODER)
    open fun duikerErrorDecoder(): ErrorDecoder {
        log.debug("Register Duiker Error Decoder")
        return ErrorDecoder { methodKey, response ->
            log.error("Decode error: {}", methodKey)
            RuntimeException(methodKey)
        }
    }
}
