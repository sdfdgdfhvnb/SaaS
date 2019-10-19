package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.*;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ExportService;
import com.itheima.vo.ExportProductResult;
import com.itheima.vo.ExportResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 出货表实现类
 */
@Service
public class ExportServiceImpl implements ExportService {

    // 报运单
    @Autowired
    private ExportDao exportDao;
    // 报运单 - 商品
    @Autowired
    private ExportProductDao exportProductDao;
    // 报运单 - 商品附件
    @Autowired
    private ExtEproductDao extEproductDao;

    // 购销合同
    @Autowired
    private ContractDao contractDao;
    // 购销合同 - 货物
    @Autowired
    private ContractProductDao contractProductDao;
    // 购销合同 - 附件
    @Autowired
    private ExtCproductDao extCproductDao;

    // 分页
    @Override
    public PageInfo<Export> findByPage(ExportExample example, int pageNum, int pageSize) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询条件
        List<Export> exportList = exportDao.selectByExample(example);

        return new PageInfo<>(exportList);
    }

    @Override
    public void updateExport(ExportResult result) {

        //1.查询报运单
        Export export = exportDao.selectByPrimaryKey(result.getExportId());
        //2.设置报运单属性（状态，和说明）
        export.setState(result.getState());
        export.setRemark(result.getRemark());
        exportDao.updateByPrimaryKeySelective(export);

        //3.循环处理报运商品
        for (ExportProductResult epr : result.getProducts()) {
            ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(epr.getExportProductId());
            //4.对报运商品的税收修改
            exportProduct.setTax(epr.getTax());
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }
    }

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    /**
     * 生成报运单
     * 1.往co_export报运单添加报运单数据 【数据来源: 页面输入】
     * 1.往co_export_product表添加商品数据 【数据来源: 根据购销合同id查询货物】
     * 1.往co_ext_eproduct表添加商品附件数据 【数据来源: 根据购销合同id查询附件】
     *
     * 需求细节:
     * 1.合同号就是购销合同的合同号,多个合同用空格隔开
     * 2.生成报运单后,修改购销合同的state=2
     */
    @Override
    public void save(Export export) {
        //1. 设置报运单参数：id、制单时间、创建时间、状态（合同号、商品数量、附件数量）
        export.setId(UUID.randomUUID().toString());
        export.setInputDate(new Date());
        export.setCreateTime(new Date());
        export.setState(0);
        // 获取购销合同ids  (1,2,3)
        String contractIds = export.getContractIds();
        String[] array = contractIds.split(",");
        List<String> ids = Arrays.asList(array);

        // 定义保存多个合同号
        String customerContract = "";
        // 根据购销合同ids， 查询
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andIdIn(ids);
        List<Contract> contractList = contractDao.selectByExample(contractExample);
        for (Contract contract : contractList) {
            // 空格隔开
            customerContract += contract.getContractNo() + " ";
            // 修改购销合同state=2
            contract.setState(2);
            // 修改
            contractDao.updateByPrimaryKeySelective(contract);
        }
        // 设置合同号
        export.setCustomerContract(customerContract);

        // 定义map，存储货物id、商品id
        Map<String,String> map = new HashMap<>();

        //2. 报运单添加商品
        //【数据来源：根据购销合同id查询货物】
        ContractProductExample contractProductExample = new ContractProductExample();
        contractProductExample.createCriteria().andContractIdIn(ids);
        List<ContractProduct> productList =
                contractProductDao.selectByExample(contractProductExample);
        // 遍历货物
        for (ContractProduct contractProduct : productList) {
            // 货物--->商品
            ExportProduct exportProduct = new ExportProduct();
            //org.springframework.beans.BeanUtils
            BeanUtils.copyProperties(contractProduct,exportProduct);
            exportProduct.setExportId(export.getId());
            exportProduct.setId(UUID.randomUUID().toString());
            // 保存商品
            exportProductDao.insertSelective(exportProduct);

            // 保存 货物id、商品id
            map.put(contractProduct.getId(),exportProduct.getId());
        }

        //3. 报运单添加商品附件
        //【数据来源：根据购销合同id查询附件】
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdIn(ids);
        List<ExtCproduct> extCproductList =
                extCproductDao.selectByExample(extCproductExample);
        // 遍历附件
        for (ExtCproduct extCproduct : extCproductList) {
            // 货物附件--->商品附件
            ExtEproduct extEproduct = new ExtEproduct();
            BeanUtils.copyProperties(extCproduct,extEproduct);
            extEproduct.setId(UUID.randomUUID().toString());
            extEproduct.setExportId(export.getId());
            /**
             * 已知： 货物id   extCproduct.getContractProductId()
             * 缺少： 商品id   map.get(extCproduct.getContractProductId())
             */
            // 根据货物id，获取map中的商品id
            String exportProductId = map.get(extCproduct.getContractProductId());
            // 设置附件关联的商品id
            extEproduct.setExportProductId(exportProductId);
            // 保存附件
            extEproductDao.insertSelective(extEproduct);
        }


        //4. 保存报运单
        // 设置商品数量、附件数量
        export.setProNum(productList.size());
        export.setExtNum(extCproductList.size());
        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {

        // 1.修改报运单
        exportDao.updateByPrimaryKeySelective(export);

        // 2.修改商品
        List<ExportProduct> exportProducts = export.getExportProducts();

        if (exportProducts != null && exportProducts.size() > 0) {
            for (ExportProduct exportProduct : exportProducts) {

                // 2.1 货物遍历商品
                exportProductDao.updateByPrimaryKeySelective(exportProduct);

            }
        }
    }

    @Override
    public void delete(String id) {
        exportDao.deleteByPrimaryKey(id);
    }

}
