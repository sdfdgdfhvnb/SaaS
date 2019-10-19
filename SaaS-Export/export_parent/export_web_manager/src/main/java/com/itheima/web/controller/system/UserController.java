package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
 * 用户controller
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    // 注入service
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 用户分页列表
     */
    @RequestMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        // 获取subject对象
        Subject subject = SecurityUtils.getSubject();

        // 权限校验
        subject.checkPermission("用户管理");

        // 1.调用service查询用户列表
        PageInfo<User> pageInfo = userService.findByPage(getLoginCompanyId(), pageNum, pageSize);

        // 2.将用户保存到request域
        request.setAttribute("pageInfo", pageInfo);

        // 3.跳转到对象的页面
        return "system/user/user-list";
    }

    /**
     * 进入添加页面
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) {

        // 查询所有的部门，作为u添加页面的下拉框的列表显示
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());

        // 保存
        model.addAttribute("deptList", deptList);

        // 返回
        return "system/user/user-add";
    }

    /**
     * 添加或者修改
     */
    @RequestMapping("edit")
    public String edit(User user) {

        // 企业id和名字先写死
        user.setCompanyId(getLoginCompanyId());
        user.setCompanyName(getLoginCompanyName());

        // 根据id判断是添加还是修改
        if (StringUtils.isEmpty(user.getId())) {

            // 如果id为null，说明是添加
            userService.save(user);

            // 添加用户成功,发送邮件
            if (user.getEmail() != null && !"".equals(user.getEmail())) {

                String email = user.getEmail();

                // 处理发送邮件的业务
                String subject = "新员工入职通知";
                String content = "欢迎你来到SaasExport大家庭，我们是一个充满激情的团队，不是996哦!";

                // 发送消息
                Map<String, String> map = new HashMap<>();
                map.put("email",email);
                map.put("subject",subject);
                map.put("content",content);
                rabbitTemplate.convertAndSend("msg.email",map);
            }

        } else {

            // 若果id不为null,说明是修改
            userService.update(user);
        }

        return "redirect:/system/user/list.do";
    }

    /**
     * 进入修改页面
     * http://localhost:8080/system/user/toUpdate.do?id=100
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id, Model model) {

        // 根据id查询用户
        User user = userService.findById(id);

        // 查询所有的部门,作为下拉框的显示列表
        List<Dept> deptList = deptService.findAll(getLoginCompanyId());

        // 保存
        model.addAttribute("user", user);
        model.addAttribute("deptList", deptList);

        // 返回
        return "system/user/user-update";
    }

    /**
     * 删除用户
     * 返回数据：{message:""}
     */
    @RequestMapping("delete")
    @ResponseBody       // 自动把方法返回的对象，转换为json格式
    public Map<String, String> delete(String id) {

        // 返回的数据
        Map<String, String> result = new HashMap<>();

        // 删除
        boolean flag = userService.delete(id);

        // 判断
        if (flag) {
            // 删除成功
            result.put("message", "删除成功!");
        } else {
            result.put("message", "删除失败,当前删除的部门有子部门,请先解除关系再删除!");
        }

        return result;
    }

    @Autowired
    private RoleService roleService;

    /**
     * 进入角色分配界面
     */
    @RequestMapping("roleList")
    public String roleList(String id) {

        // 1. 需要根据前端传的用户id查询到用户user
        User user = userService.findById(id);

        // 2. 需要查询到所有的角色用来显示（根据公司id查询）
        List<Role> roleList = roleService.findAll(getLoginCompanyId());

        // 3. 查询此用户所拥有的角色(根据用户id查询)
        List<Role> userRoleList = roleService.findRoleByUserId(id);

        // 4. 定义一个字符串用来保存所有的角色id
        String roleIds = "";

        if (userRoleList != null && userRoleList.size() > 0) {

            // 保存用户拥有的角色(这里没有处理最后一个逗号)
            for (Role role : userRoleList) {

                roleIds += role.getId() + ",";
            }
        }

        // 5. 保存所有的查询结果
        request.setAttribute("user", user);
        request.setAttribute("roleList", roleList);
        request.setAttribute("roleIds", roleIds);

        // 6. 返回到user-role.jsp页面
        return "system/user/user-role";
    }

    /**
     * 保存用户角色
     * 请求地址：http://localhost:8080/system/user/changeRole.do
     * 1. 需要接收前端传过来的多个角色id（用什么接收呢？数组还是集合）
     * 2. 需要接收前端传过来的用户id
     * 3. 处理changeRole.do请求
     * <p>
     * 再service实现类中处理：
     * 根据前端传过来的用户id查询pe_user_role，先删除所有的角色
     * 之后再向该用户添加权限（根据角色数组）
     */
    @RequestMapping("changeRole")
    public String changeRole(String userId, String[] roleIds) {

        // 调用service方法
        userService.changeRole(userId, roleIds);

        // 返回到用户列表页面
        return "redirect:/system/user/list.do";
    }
}
