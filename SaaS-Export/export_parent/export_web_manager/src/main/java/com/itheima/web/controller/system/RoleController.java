package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.Role;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.RoleService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色controller
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表分页
     */
    @RequestMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        // 获取企业id
        String companyId = getLoginCompanyId();

        // 调用service方法查询部门列表
        PageInfo<Role> pageInfo = roleService.findByPage(companyId, pageNum, pageSize);

        // 保存
        request.setAttribute("pageInfo", pageInfo);

        // 跳转到对象的页面
        return "system/role/role-list";
    }

    /**
     * 进入新增角色页面
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return "system/role/role-add";
    }

    /**
     * 新增角色
     */
    @RequestMapping("edit")
    public String edit(Role role) {

        // 写死companyId和companyName
        role.setCompanyId(getLoginCompanyId());
        role.setCompanyName(getLoginCompanyName());

        // 判断是否有id
        if (StringUtils.isEmpty(role.getId())) {

            // id为null
            roleService.save(role);
        } else {

            // id不为null
            roleService.update(role);
        }

        return "redirect:/system/role/list.do";
    }

    /**
     * 进入修改界面
     * 1.获取到id，根据id进行查询
     * 2.保存查询结果到request域中
     * 3.跳转到修改界面
     */
    @RequestMapping("toUpdate")
    public String update(String id) {

        // 1.获取到id，根据id进行查询
        Role role = roleService.findById(id);

        // 2.保存查询结果到request域中
        request.setAttribute("role", role);

        // 3.跳转到修改界面
        return "system/role/role-update";
    }

    /**
     * 删除角色
     */
    @RequestMapping("delete")
    public String delete(String id) {

        // 调用service删除角色
        roleService.delete(id);

        return "redirect:/system/role/list.do";
    }

    /**
     * 角色分配权限(1): 进入角色权限页面
     * 功能入口：
     * 角色列表，点击权限按钮
     * 请求地址：
     * location.ref="/system/role/roleModule.do?roleId="+id;
     */
    @RequestMapping("roleModule")
    public String roleModule(String roleId) {

        // 调用service查询角色
        Role role = roleService.findById(roleId);

        request.setAttribute("role", role);

        return "system/role/role-module";
    }

    /**
     * 角色分配权限(2): role-module.jsp发送异步请求,返回ztree格式的数据
     * 功能入口：role-module.jsp 页面的异步请求
     * 请求地址：/system/role/getZtreeNodes.do
     * 请求参数：roleId
     * 返回格式：json
     * 返回数据：[{id:2,pId:0,name="随意勾选2",checked:true,open:true}]
     */
    // 注入module
    @Autowired
    private ModuleService moduleService;

    // 返回值类型是根据返回数据格式决定的
    @RequestMapping("getZtreeNodes")
    @ResponseBody
    public List<Map<String, Object>> getZtreeNodes(String roleId) {

        List<Map<String, Object>> result = new ArrayList<>();

        // 1.查询所有权限
        List<Module> moduleList = moduleService.findAll();

        // 2.查询当前角色(role)有的权限
        List<Module> roleModuleList = moduleService.findModuleByRoleId(roleId);

        // 3.构造返回的数据
        for (Module module : moduleList) {

            // 3.1 构造map集合
            // {id:2,pId:0,name="随意勾选2",checked:true,open:true}
            Map<String, Object> map = new HashMap<>();

            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());
            map.put("open", true);

            // 3.2判断是否选中,如果当前角色已经拥有该权限(module对象),就默认选中
            if (roleModuleList.contains(module)) {
                map.put("checked", true);
            }

            // 把封装好的map数据添加到集合
            result.add(map);
        }

        return result;
    }

    /**
     * 角色分配权限(3): 角色分配权限
     * 请求地址：/system/role/updateRoleModule.do
     * 请求参数：
     *      roleId 角色id
     *      moduleIds 多个权限id，用逗号隔开
     */
    @RequestMapping("updateRoleModule")
    public String updateRoleModule(String roleId, String moduleIds) {
        roleService.updateRoleModule(roleId, moduleIds);
        return "redirect:/system/role/list.do";
    }
}
