package com.itheima.controller;

import com.itheima.pojo.Item;
import com.itheima.pojo.QueryVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 商品controller
 */
@Controller
public class ItemController {

    /**
     * 查询全部商品列表数据
     * <form action="${pageContext.request.contextPath }/queryItem.do"
     */
    @RequestMapping("queryItem")
    public ModelAndView queryItem(QueryVO queryVO,Integer[] ids) {

        // 1.创建ModelAndView
        ModelAndView mav = new ModelAndView();

        // 2.设置响应的页面
        mav.setViewName("item/list");

        return mav;
    }

    /**
     * 根据商品id查询商品:
     *      http://127.0.0.1:8080/queryItemById.do?id=1
     *
     *      1.通过处理器方法的形参,接收到请求的参数数据
     *      2.httpServletRequest:
     *          用于接收请求的参数数据
     *          springmvc框架在调用处理器方法的时候会传递该对象过来
     */
    @RequestMapping("queryItemById")
    public String queryItemById(Model model, HttpServletRequest request) {

        // 获取参数数据
        String id = request.getParameter("id");
        System.out.println("商品id参数数据:" + id);

        /**
         * 通过Model封装响应的数据
         * addAttribute: 用于封装响应的模型数据(相当于ModelAndView中的addObject方法)
         * 参数:
         *      参数一:
         *          模型名称
         *      参数二:
         *          模型数据值
         */
        model.addAttribute("model","Model响应的模型数据");

        // 响应页面
        return "item/edit";
    }

    /**
     * 修改商品,模拟保存数据库:
     *      http://127.0.0.1:8080/updateItem.do
     * 学习pojo参数类型
     */
    @RequestMapping("updateItem")
    public String updateItem(Item item) {

        // 打印商品数据
        System.out.println(item);

        return "success";
    }

    // ======================controller方法返回值====================================

    /**
     * 学习controller方法返回void
     * 1.request转发
     * 2.response重定向
     * 3.response响应数据
     */
    @RequestMapping("returnVoid")
    public void controllerReturnVoid(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 1.request转发
         *      1.1 转发是一次请求,可以获取到request中的数据
         *      1.2 转发浏览器地址栏不会发生变化
         *      1.3 转发只能在项目内部执行
         */
        /*RequestDispatcher rd = request.getRequestDispatcher("queryItem.do");

        try {
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }*/


        /**
         * 2.response重定向
         * 重定向特点：
         *      1.重定向是两次请求，不能获取旧的request中的数据
         *      2.重定向浏览器的地址栏会改变成目标url
         *      3.重定向可以在项目内部执行；也可以重定向到其它项目
         */
        try {
            response.sendRedirect("queryItem.do");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===========springmvc学习第二天===================
    /**
     * 学习json数据交互
     * 需求:
     *      1.客户端(浏览器)发起请求,请求参数是商品对象的json格式数据
     *      2.在处理器方法中,通过商品Item对象,接收到请求的商品参数数据
     *      3.处理器方法,完成请求处理。响应java对象,在客户端浏览器展示json格式数据
     *
     * 注解说明:
     *      @RequestBody: 把请求的json格式数据，转换成java对象
     *      @ResponseBody: 把响应的java对象，转换成json格式的数据
     */
    @RequestMapping("testJson")
    @ResponseBody
    public Item testJson(@RequestBody Item item) {

        // 打印商品对象
        System.out.println(item);
        return item;
    }
}
