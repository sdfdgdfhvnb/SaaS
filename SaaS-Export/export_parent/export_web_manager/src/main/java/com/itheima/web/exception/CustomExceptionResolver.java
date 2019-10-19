package com.itheima.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 */
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {


    /**
     * 1.跳转到错误页面
     * 2.携带错误信息
     */
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {

        // 打印异常信息
        ex.printStackTrace();

        ModelAndView mov = new ModelAndView();

        mov.addObject("ex",ex);
        mov.addObject("errorMsg","服务器繁忙...");
        mov.setViewName("error");

        return mov;
    }
}
