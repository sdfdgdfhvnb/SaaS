package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 学习@RequestMapping注解:
 * 1.配置请求的url
 * 2.限制http请求方法
 * 3.分类管理url
 */
// 在类上面使用,对请求的url路径进行分类管理(窄化请求)
@Controller
@RequestMapping("hello")
public class RequestMappingController {

    /**
     * 学习@RequestMapping注解:
     * 1.配置请求的url
     *      属性:
     *          value:是一个数组,说明可以配置多个url
     *      细节:
     *          "/"和后缀".do"可以省略
     * 2.限制http请求方法
     *      http常见的请求方法: post/get/put/delete
     *      属性:
     *          method:指定允许访问的方法
     *      细节:
     *          method = {RequestMethod.POST}:只有post请求可以
     *          method = {RequestMethod.POST,RequestMethod.GET}:只有post/get请求可以
     */
    @RequestMapping(value = {"requestMapping.do"}, method = {RequestMethod.GET})
    public ModelAndView requestMapping() {

        // 1.创建ModelAndView
        ModelAndView mav = new ModelAndView();

        // 2.设置响应的数据模型
        mav.addObject("hello", "RequestMapping注解!");

        // 3.设置响应的页面
        mav.setViewName("success");

        return mav;
    }
}
