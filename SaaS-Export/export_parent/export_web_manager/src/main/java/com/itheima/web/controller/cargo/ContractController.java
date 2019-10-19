package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.system.User;
import com.itheima.service.cargo.ContractService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    /**
     * 1. 分页查询
     */
    @RequestMapping("list")
    public String list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {

        // 构造条件对象
        ContractExample example = new ContractExample();
        // 根据创建时间排序显示
        example.setOrderByClause("create_time desc");
        ContractExample.Criteria criteria = example.createCriteria();
        // 添加条件： 企业id
        criteria.andCompanyIdEqualTo(getLoginCompanyId());

        /**
         * 构造条件--细粒度权限控制。根据登录用户的等级显示购销合同
         * 用户级别：
         *          0-saas管理员
         *          1-企业管理员
         *          2-管理所有下属部门和人员
         *          3-管理本部门
         *          4-普通员工
         */
        User user = getUser();
        if (user.getDegree() == 4) {
            //-- 1) 普通用户登陆，degree=4，只能查看自己创建的购销合同
            //SELECT * FROM co_contract WHERE create_by='登陆用户的id'
            criteria.andCreateByEqualTo(user.getId());
        } else if (user.getDegree() == 3) {
            //-- 2) 部门经理登陆，degree=3，能查看本部门所有员工创建的购销合同
            //SELECT * FROM co_contract WHERE create_dept='登陆用户的部门'
            criteria.andCreateDeptEqualTo(user.getDeptId());
        } else if (user.getDegree() == 2) {
            //-- 3) 大部门经理登陆，degree=2，能查看本部门及所有子部门员工创建的购销合同
            //SELECT * FROM co_contract WHERE FIND_IN_SET(create_dept,getDeptChild('100'))
            PageInfo<Contract> pageInfo =
                    contractService.selectByDeptId(user.getDeptId(), pageNum, pageSize);
            request.setAttribute("pageInfo", pageInfo);
            return "cargo/contract/contract-list";
        }

        // 调用service，分页
        PageInfo<Contract> pageInfo =
                contractService.findByPage(example,pageNum,pageSize);

        // 返回
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/contract/contract-list";
    }

    /**
     * 2. 进入添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    /**
     * 3. 添加或修改
     */
    @RequestMapping("/edit")
    public String edit(Contract contract) {
        // 企业id、名称先写死。后面从登陆用户中获取。
        contract.setCompanyId(getLoginCompanyId());
        contract.setCompanyName(getLoginCompanyName());

        // 根据id判断添加还是修改
        if (StringUtils.isEmpty(contract.getId())) {

            // 细粒度权限控制: 记录购销合同的创建人,创建人所属部门
            contract.setCreateBy(getUser().getId());
            contract.setCreateDept(getUser().getDeptId());

            // 如果id为空，说明是添加
            contractService.save(contract);
        } else {
            contractService.update(contract);
        }

        // 添加成功，重定向到列表
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 4. 进入修改页面
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id, Model model) {
        // 根据id查询
        Contract contract = contractService.findById(id);
        // 保存
        model.addAttribute("contract", contract);
        // 转发
        return "cargo/contract/contract-update";
    }

    /**
     * 5. 删除
     */
    @RequestMapping("/delete")
    public String delete(String id){
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 查看：http://localhost:8080/cargo/contract/toView.do?id=4
     * 提交：http://localhost:8080/cargo/contract/submit.do?id=4
     * 取消：http://localhost:8080/cargo/contract/cancel.do?id=4
     */
    @RequestMapping("/toView")
    public String toView(String id){
        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "cargo/contract/contract-view";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        /*提交： state状态改为1*/
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);
        // 动态更新
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        /*提交： state状态改为1*/
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);
        // 动态更新
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }
}