package com.zdan91.duiker.exception;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BuffException.class)
    @ResponseBody
    public ResponseEntity exceptionHandler(BuffException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(ex.getMessage());

    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity exceptionHandler(RuntimeException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("system is maintenance");
    }
}
