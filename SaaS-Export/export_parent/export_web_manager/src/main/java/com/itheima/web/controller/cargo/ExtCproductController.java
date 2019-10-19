package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.ExtCproductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 附件controller
 */
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private FactoryService factoryService;
    @Reference
    private ExtCproductService extCproductService;

    /**
     * 1.显示附件列表
     * 功能入口：product-list.jsp
     * <${ctx}/cargo/extCproduct/list.do?contractId=${o.contractId}&contractProductId=/>
     */
    @RequestMapping("list")
    public String list(
            String contractId,String contractProductId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize) {

        //1.查询货物的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria2 = factoryExample.createCriteria();
        criteria2.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //2.查询当前货物下的所有附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo =
                extCproductService.findByPage(extCproductExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);

        //3.设置页面的基本参数：id
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);

        return "cargo/extc/extc-list";
    }

    /**
     * 2.保存或者修改方法
     * 从前端传过来的参数: contractProductId contractId factoryName
     * 提交表单，出入附件
     */
    @RequestMapping("edit")
    public String edit(ExtCproduct extCproduct) {

        // 根据id是否存在判断是保存还是修改
        if (StringUtils.isEmpty(extCproduct.getId())) {

            // 保存
            extCproductService.save(extCproduct);

        } else {

            // 修改
            extCproductService.update(extCproduct);

        }

        return "redirect:/cargo/extCproduct/list.do?contractId=" +extCproduct.getContractId() +
                "&contractProductId=" + extCproduct.getContractProductId();
    }

    /**
     * 3.进入附件的修改页面
     * 请求地址: /cargo/extCproduct/toUpdate.do?id=&contractId=$&contractProductId=
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId) {

        // 1.设置页面的基本参数(购销合同id和货物id)
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);

        // 2.查询附件,此时查询出来的附件存在id,所以不需要将id存储到request域中
        ExtCproduct extCproduct = extCproductService.findById(id);

        // 3.查询生产厂家作为厂家列表显示
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);

        // 保存
        request.setAttribute("extCproduct", extCproduct);
        request.setAttribute("factoryList", factoryList);

        // 4.返回
        return "cargo/extc/extc-update";
    }

    /**
     * 删除附件
     * 请求地址: /cargo/extCproduct/delete.do?id=&contractId=&contractProductId=
     */
    @RequestMapping("delete")
    public String delete(String id, String contractId, String contractProductId) {

        // 删除附件
        extCproductService.delete(id);

        return "redirect:/cargo/extCproduct/list.do?contractId=" + contractId +"&contractProductId=" + contractProductId;
    }
}