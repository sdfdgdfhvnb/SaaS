package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 入门案例程序controller
 */
@Controller
public class HelloController {

    /**
     * ModelAndView:模型和视图,用于封装响应的模型(数据):用于封装响应的视图(页面)
     * @RequestMapping: 用于配置请求的url路径
     */
    @RequestMapping("/hello.do")
    public ModelAndView hello() {

        // 1.创建ModelAndView
        ModelAndView mav = new ModelAndView();

        /**
         * 2.设置响应的模型数据
         * addObject: 封装模型数据的方法
         * 参数:
         *      参数一:模型的名字
         *      参数二:模型数据值
         */
        mav.addObject("hello", "springmvc!");

        /**
         * 3.设置响应的页面
         * setViewName:设置响应的页面(页面的路径)
         * 参数:
         *      设置页面的物理路径
         */
        mav.setViewName("/WEB-INF/jsp/success.jsp");

        return mav;
    }
}
