package com.zdan91.duiker.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 处理自定义异常
 *
 * @author shijikun
 * @date 15/12/2017 4:02 PM
 */
@Component
public class GlobalExceptionHandler extends AbstractHandlerExceptionResolver {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 将默认的500状态码,修改为自定义的 code
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
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
