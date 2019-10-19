package com.itheima.web.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * company controller
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    // 定义companyService接口对象,注入业务类
    @Reference
    private CompanyService companyService;

    /**
     * 查询企业列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request) {

        // 调用service方法
        List<Company> list = companyService.findAll();

        request.setAttribute("list", list);

        return "company/company-list";
    }

    /**
     * 测试方法:
     * 请求地址：http://localhost:8080/company/save.do?birth=1998-09-09
     * 访问结果
     * A. 浏览器
     * HTTP Status 400 – Bad Request  封装请求参数的问题。
     * B. 后台日志
     * Failed to convert value of type 'java.lang.String' to required type 'java.util.Date';
     * 解决：
     * 1. 自定义类型转换器，写一个类实现Converter接口
     * 2. 配置转换器工厂
     */
    @RequestMapping("save")
    public String save(Date birth) {
        System.out.println(birth);
        return "success";
    }

    /**
     * 进入添加页面
     * 功能入口: 企业列表点击新建
     * 请求地址：http:localhost:8080/company/toAdd.do
     * 响应地址： /WEB-INF/pages/company/company-add.jsp
     *
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return "company/company-add";
    }

    /**
     * 添加企业/修改企业
     *
     * @param company
     * @return
     */
    @RequestMapping("edit")
    public String edit(Company company) {

        // 根据id判断是添加还是修改
        if (StringUtils.isEmpty(company.getId())) {

            // 如果id是null
            companyService.save(company);
        } else {

            // 如果id不是null
            companyService.update(company);
        }

        // 添加成功，重定向到list界面
        return "redirect:/company/list.do";
    }

    /**
     * 进入修改页面
     * 功能入口：company-list.jsp 列表点击编辑
     * 请求地址: http://localhost:8080/company/toUpdate.do
     * 请求参数：id  修改企业的id
     * 响应地址： /WEB-INF/pages/company/company-update.jsp
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id, Model model) {

        // 根据企业id查询
        Company company = companyService.findById(id);

        // 保存
        model.addAttribute("company", company);

        // 转发
        return "company/company-update";
    }

    /**
     * 删除企业
     * 功能入口: company-list.jsp
     * 请求地址：http://localhost:8080/company/delete.do?id=............
     */
    @RequestMapping("delete")
    public String delete(String id) {

        // 根据id删除企业
        companyService.delete(id);

        return "redirect:/company/list.do";
    }
}
