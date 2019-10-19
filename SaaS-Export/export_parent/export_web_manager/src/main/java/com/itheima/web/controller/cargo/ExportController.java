package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractService;
import com.itheima.service.cargo.ExportProductService;
import com.itheima.service.cargo.ExportService;
import com.itheima.vo.ExportProductVo;
import com.itheima.vo.ExportResult;
import com.itheima.vo.ExportVo;
import com.itheima.web.controller.BaseController;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同controller
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    // 注入合同service
    @Reference
    private ContractService contractService;
    // 注入出口service
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;

    /**
     * 合同列表: 只显示购销合同状态为1的合同
     */
    @RequestMapping("contractList")
    public String contractList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {

        // 查询条件1: 公司id
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());

        // 查询条件2: 状态为1(已上报)
        criteria.andStateEqualTo(1);
        PageInfo<Contract> pageInfo = contractService.findByPage(contractExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);

        // 返回
        return "/cargo/export/export-contractList";
    }

    /**
     * 出口报运单列表
     * 功能入口: export-list.jsp
     */
    @RequestMapping("list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        // 构造条件对象
        ExportExample exportExample = new ExportExample();

        // 根据创建时间显示
        exportExample.setOrderByClause("create_time desc");
        ExportExample.Criteria criteria = exportExample.createCriteria();

        // 添加条件: 企业id
        criteria.andCompanyIdEqualTo(getLoginCompanyId());

        // 调用service,分页
        PageInfo<Export> pageInfo = exportService.findByPage(exportExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);

        // 返回
        return "cargo/export/export-list";
    }

    /**
     * 进入报运单的添加页面
     * 请求参数:
     *      id  多个
     */
    @RequestMapping("toExport")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    /**
     * 添加或者修改
     */
    @RequestMapping("edit")
    public String edit(Export export) {

        export.setCompanyId(getLoginCompanyId());
        export.setCompanyName(getLoginCompanyName());

        // 判断是修改还是保存
        if (StringUtils.isEmpty(export.getId())) {

            // 保存
            exportService.save(export);

        } else {

            // 修改
            exportService.update(export);

        }

        return "redirect:/cargo/export/list.do";
    }

    /**
     * 进入报运单修改页面
     */
    @RequestMapping("toUpdate")
    public String toUpdate(String id) {

        // 1.根据id查询报运单
        Export export = exportService.findById(id);
        request.setAttribute("export", export);

        // 2.查询此报运单下的所有商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(example);

        // 保存
        request.setAttribute("export", export);
        request.setAttribute("eps", exportProductList);

        // 返回
        return "cargo/export/export-update";
    }

    /**
     * 出口报运列表,点击取消
     */
    @RequestMapping("cancel")
    public String cancel(String id) {

        // 根据id查询报运单
        Export export = exportService.findById(id);

        // 修改报运单的状态(state=0 为草稿)
        export.setState(0);

        // 保存修改
        exportService.update(export);

        // 返回
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 出口报运列表,点击提交
     */
    @RequestMapping("submit")
    public String submit(String id) {

        // 根据id查询报运单
        Export export = exportService.findById(id);

        // 修改报运单的状态(state=1 为已上报)
        export.setState(1);

        // 保存修改
        exportService.update(export);

        // 返回
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 电子报运
     */
    @RequestMapping("exportE")
    public String exportE(String id) {

        // 1.根据报运单id查询报运对象
        Export export = exportService.findById(id);

        // 2.根据报运单id查询报运商品列表
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(exportProductExample);

        // 3.构造电子报运的vo对象,并赋值
        ExportVo exportVo = new ExportVo();
        BeanUtils.copyProperties(export, exportVo);
        exportVo.setExportId(export.getId());

        // 构造报运商品数据
        List<ExportProductVo> products = new ArrayList<>();

        // 遍历报运商品集合复制数据
        for (ExportProduct ep : eps) {

            ExportProductVo epv = new ExportProductVo();
            BeanUtils.copyProperties(ep, epv);
            epv.setExportProductId(ep.getId());
            products.add(epv);
        }
        exportVo.setProducts(products);

        // 4.电子报运
        WebClient client = WebClient.create("http://localhost:8081/ws/export/user");
        client.post(exportVo);

        // 5.查询报运结果
        client = WebClient.create("http://localhost:8081/ws/export/user" + id);
        ExportResult result = client.get(ExportResult.class);

        // 6.调用service完成报运结果的入库
        exportService.updateExport(result);
        return "redirect:/cargo/export/list.do";
    }
}
