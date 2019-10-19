package com.itheima.web.controller;

import com.itheima.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 抽取controller部分功能作为BaseController
 */
public class BaseController {

    // 注入request
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    // 返回登录用户的企业id
    public String getLoginCompanyId() {

        // 得到企业id
        return getUser().getCompanyId();
    }


    // 返回登陆用户的企业名称
    public String getLoginCompanyName(){

        // 得到企业名称
        return getUser().getCompanyName();
    }

    // 将得到的用户抽取成一个方法
    public User getUser() {
        return (User) session.getAttribute("loginUser");
    }
}
