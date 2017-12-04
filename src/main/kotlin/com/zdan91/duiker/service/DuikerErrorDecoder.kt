package com.zdan91.duiker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.zdan91.duiker.config.Constants
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.RuntimeException

/**
 * Duiker Error Decoder.
 * Configure spring profiles includes: <b>duiker-feign-error-decoder</b> to enable this error decoder.
 * Decoder Exception from rpc service.
 *
 * @author hookszhang
 */
@Service
@Profile(Constants.DUIKER_FEIGN_ERROR_DECODER)
class DuikerErrorDecoder : ErrorDecoder {

    private val log = LoggerFactory.getLogger(DuikerErrorDecoder::class.java)

    private val mapper = ObjectMapper()

    override fun decode(methodKey: String, response: Response): Exception {
        val map = mapper.readValue(response.body().asInputStream(), Map::class.java)
        log.error(map.toString())
        // TODO implements exception transform
        return RuntimeException(methodKey)
    }
}
