package com.zdan91.duiker.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GlobalExceptionHandler extends AbstractHandlerExceptionResolver {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof CustomException) {
            try {
                response.sendError(((CustomException) ex).getCode());
            } catch (IOException e) {
                log.error("写入异常信息时出错 :", e);
            }
        } else {
            return null;
        }
        return new ModelAndView();
    }
}
