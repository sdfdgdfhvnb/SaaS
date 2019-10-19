package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.service.cargo.ContractService;
import com.itheima.vo.ContractProductVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 货运服务工程服务
 */
@Service
public class ContractProductServiceImpl implements ContractProductService {

    // 注入dao
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ExtCproductDao extCproductDao;

    /**
     * 分页查询
     *
     * @param ContractProductExample 分页查询的参数
     * @param pageNum                当前页
     * @param pageSize               页大小
     * @return
     */
    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample ContractProductExample, int pageNum, int pageSize) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        List<ContractProduct> list = contractProductDao.selectByExample(ContractProductExample);

        // 返回
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @param ContractProductExample
     */
    @Override
    public List<ContractProduct> findAll(ContractProductExample ContractProductExample) {
        return contractProductDao.selectByExample(ContractProductExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {

        // 1.设置id
        contractProduct.setId(UUID.randomUUID().toString());

        // 2.设置货物金额
        Double amount = 0d;
        Double price = contractProduct.getPrice();
        Integer cnumber = contractProduct.getCnumber();

        if (price != null && cnumber != null) {

            // 设置金额
            amount = price * cnumber;
        }
        contractProduct.setAmount(amount);

        // 3.修改购销合同: 总金额,货物数量
        Contract contract = contractService.findById(contractProduct.getContractId());

        // 3.1 总金额 = 总金额 + 货物金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);

        // 3.2 货物数量 = 货物数量 + 1
        contract.setProNum(contract.getProNum() + 1);

        // 3.3 修改购销合同
        contractService.update(contract);

        // 4. 添加货物
        contractProductDao.insertSelective(contractProduct);
    }

    /**
     * 修改
     *
     * @param contractProduct
     */
    @Override
    public void update(ContractProduct contractProduct) {

        // 1.计算修改之后的货物金额
        Double amount = 0d;
        Double price = contractProduct.getPrice();
        Integer cnumber = contractProduct.getCnumber();

        if (price != null && cnumber != null) {

            // 设置金额
            amount = price * cnumber;
        }

        // 2.货物修改之前的货物金额
        ContractProduct product = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        Double oldAmount = product.getAmount();

        // 3.修改购销合同总金额 = 总金额 + 修改后 - 修改前
        Contract contract = contractService.findById(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount - oldAmount);
        contractService.update(contract);

        // 修改货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
    }

    /**
     * 删除部门
     *
     * @param id
     */
    @Override
    public void delete(String id) {

        // 1.根据货物id查询，货物货物金额
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        Double amount = contractProduct.getAmount();

        // 2.查询货物下所有附件，累加附件金额，删除附件
        Double extAmount = 0d;

        // 2.1 根据货物id查询附件
        ExtCproductExample example = new ExtCproductExample();
        example.createCriteria().andContractProductIdEqualTo(id);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        //B2. 遍历附件集合
        if (list != null && list.size()>0) {
            for (ExtCproduct extCproduct : list) {
                // 累加附件金额
                extAmount += extCproduct.getAmount();
                // 删除附件
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

        //C.购销合同总金额 = 总金额 - 删除的货物金额 - 附件金额
        Contract contract = contractService.findById(contractProduct.getContractId());
        // 修改总金额
        contract.setTotalAmount(contract.getTotalAmount() - amount - extAmount);
        // 修改货物数量、附件数量
        contract.setProNum(contract.getProNum() - 1);
        contract.setExtNum(contract.getExtNum() - list.size());
        // 修改购销合同
        contractService.update(contract);

        //D.删除货物
        contractProductDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据船期统计货物
     *
     * @param shipTime
     * @param companyId
     */
    @Override
    public List<ContractProductVo> findByShipTime(String shipTime, String companyId) {
        return contractProductDao.findByShipTime(shipTime, companyId);
    }
}
