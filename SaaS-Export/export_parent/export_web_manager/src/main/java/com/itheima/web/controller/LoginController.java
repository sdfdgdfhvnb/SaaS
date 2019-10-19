package com.itheima.web.controller;


import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    // 注入userService和moduleService
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;

    /**
     * 执行流程
     * 1.访问首页
     *      http:localhost:8080/index.jsp
     * 2. index.jsp
     *      location.href = "login.do"
     * 3. 转发到main.jsp
     */
    /*@RequestMapping("/login")
    public String login(String email, String password) {

        // 先判断输入的数据是不是空
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {

            // 跳转到登录页面
            request.setAttribute("error", "账号和密码不能为空");
            return "forward:/login.jsp";
        }

        // 根据email查询用户
        User user = userService.findByEmail(email);

        // 判断password和用户的密码是否一致
        if (password.equals(user.getPassword())) {
            // 跳转到主页面
            session.setAttribute("loginUser", user);

            // 登录成功之后查看用户的权限
            // 需要获取登录用户的用户id(直接判断用户的degree可以判断出0和1，但是无法得到其他degree的权限了)
            List<Module> moduleList = userService.findModuleByUserId(user.getId());
            // 写进session
            session.setAttribute("moduleList", moduleList);

            return "home/main";
        }

        // 否则,登录失败,跳转到登录页面
        request.setAttribute("error", "用户名或者密码错误");
        return "forward:/login.jsp";
    }*/


    // 通过shiro实现登录
    @RequestMapping("/login")
    public String login(String email, String password) {

        // 1.先判断输入的数据是不是空
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {

            // 跳转到登录页面
            request.setAttribute("error", "账号和密码不能为空");
            return "forward:/login.jsp";
        }

        try {
            // 2.shiro登录认证
            // 2.1 创建Subject对象,subject相当于用户
            Subject subject = SecurityUtils.getSubject();

            // 2.2 创建token对象,封装用户名和密码
            AuthenticationToken token = new UsernamePasswordToken(email, password);

            // 2.3 登录认证(自动去到realm的认证方法)
            subject.login(token);

            // 3.登录成功
            // 获取用户的身份对象(对应realm中认证方法返回的对象的构造方法的第一个参数)
            User user = (User) subject.getPrincipal();
            session.setAttribute("loginUser", user);

            // 需要获取登录用户的用户id(直接判断用户的degree可以判断出0和1，但是无法得到其他degree的权限了)
            List<Module> moduleList = moduleService.findModuleByUserId(user.getId());
            // 写进session
            session.setAttribute("moduleList", moduleList);

            return "home/main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            // 登录失败
            request.setAttribute("error", "用户名或者密码错误!");
            return "forward:/login.jsp";
        }
    }

    /**
     * 注销功能:
     * 请求地址：http://localhost:8080/logout.do
     * 1. 删除用户，之后删除session
     * 2. 跳转到登录页面
     */
    @RequestMapping("logout")
    public String logout() {

        // 得到session并且删除用户
        session.removeAttribute("loginUser");

        // 删除session
        session.invalidate();

        // 跳转到登录页面
        return "forward:/login.jsp";
    }

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
