package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门controller
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    /**
     * 1.分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        // 企业id先写死，后面完成登录后再从登录用户那里取
        String companyId = getLoginCompanyId();

        PageInfo<Dept> pageInfo = deptService.findByPage(companyId, pageNum, pageSize);

        // 返回数据
        request.setAttribute("pageInfo", pageInfo);

        return "system/dept/dept-list";
    }

    /**
     * 2.进入添加页面
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) {

        // 查询所有的部门，作为添加页面的部门下拉框列表
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());

        model.addAttribute("deptList", deptList);

        return "system/dept/dept-add";
    }

    /**
     * 3.添加或修改部门
     * http://localhost:8080/system/dept/edit.do
     */
    @RequestMapping("edit")
    public String edit(Dept dept) {

        // 设置企业信息,先写死,之后从登录用户那里取出来
        dept.setCompanyId(getLoginCompanyId());
        dept.setCompanyName(getLoginCompanyName());

        // 根据id判断是添加还是修改
        if (StringUtils.isEmpty(dept.getId())) {

            // id为空,说明是添加
            deptService.save(dept);

        } else {

            // id不为空则为修改
            deptService.update(dept);
        }

        return "redirect:/system/dept/list.do";
    }

    /**
     * 4.进入修改页面
     * http://localhost:8080/system/dept/toUpdate/do?id=..
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id, Model model) {

        // 根据id查询部门
        Dept dept = deptService.findById(id);

        // 查询所有部门作为修改页面的下拉框数据
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());

        // 保存
        model.addAttribute("dept", dept);
        model.addAttribute("deptList", deptList);

        // 转发
        return "system/dept/dept-update";
    }

    /**
     * 5.删除部门
     * 返回数据：{message:""}
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String, String> delete(String id) {

        // 1.返回的数据
        Map<String, String> result = new HashMap<>();

        // 2.删除
        boolean flag = deptService.delete(id);

        // 判断
        if (flag) {
            result.put("message", "删除成功!");
        } else {
            result.put("message", "删除失败,删除的部门存在子部门,需要先删除子部门!");
        }

        return result;
    }
}
