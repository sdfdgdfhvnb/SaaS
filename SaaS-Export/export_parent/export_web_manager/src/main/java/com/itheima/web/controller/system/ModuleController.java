package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.service.system.ModuleService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 模块controller
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    // 注入service
    @Autowired
    private ModuleService moduleService;

    /**
     * 模块分页列表
     */
    @RequestMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5")Integer pageSize) {

        // 1.调用service查询模块列表
        PageInfo<Module> pageInfo = moduleService.findByPage(pageNum, pageSize);

        // 保存
        request.setAttribute("pageInfo", pageInfo);

        // 返回
        return "system/module/module-list";
    }

    /**
     * 进入添加页面
     */
    @RequestMapping("toAdd")
    public String toAdd() {

        List<Module> menus = moduleService.findAll();

        request.setAttribute("menus", menus);

        return "system/module/module-add";
    }

    /**
     * 添加或者修改
     */
    @RequestMapping("edit")
    public String edit(Module module) {

        // 根据id判断是添加或者修改
        if (StringUtils.isEmpty(module.getId())) {

            // 如果id为null
            moduleService.save(module);

        } else {

            // 如果id不为null
            moduleService.update(module);
        }

        // 重定向
        return "redirect:/system/module/list.do";
    }

    /**
     * 进入修改页面
     * http://localhost:8080/system/module/toUpdate.do?id=100
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id, Model model) {

        // 根据id查询
        Module module = moduleService.findById(id);

        // 查询所有的模块列表作为下拉框显示列表
        List<Module> moduleList = moduleService.findAll();

        // 保存
        model.addAttribute("module", module);
        model.addAttribute("moduleList", moduleList);

        // 转发
        return "system/module/module-update";
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public String delete(String id) {

        // 调用service删除模块
        moduleService.delete(id);

        // 返回
        return "redirect:/system/module/list.do";
    }
}
