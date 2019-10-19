package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.service.cargo.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    // 注入dao
    @Autowired
    private ExtCproductDao extCproductDao;
    // 注入合同dao
    @Autowired
    private ContractDao contractDao;

    /**
     * 分页查询
     *
     * @param extCproductExample
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<ExtCproduct> findByPage(ExtCproductExample extCproductExample, int pageNum, int pageSize) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);

        return new PageInfo<>(extCproductList);
    }

    /**
     * 查询所有
     *
     * @param extCproductExample
     */
    @Override
    public List<ExtCproduct> findAll(ExtCproductExample extCproductExample) {
        return extCproductDao.selectByExample(extCproductExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param extCproduct
     */
    @Override
    public void save(ExtCproduct extCproduct) {
        // 1.设置基本属性(id)
        extCproduct.setId(UUID.randomUUID().toString());

        // 2.计算总金额
        Double amount = 0d;
        Integer cnumber = extCproduct.getCnumber();
        Double price = extCproduct.getPrice();

        // 2.1 判断是否为null
        if (cnumber != null && price != null) {
            amount = cnumber * price;
        }

        // 3.添加附件的总金额
        // 此时extCproduct.getAmount()为0,所以不需要这个
        extCproduct.setAmount(amount);

        // 4.保存附件
        extCproductDao.insertSelective(extCproduct);

        // 5.根据附件所属购销合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());

        // 6.修改购销合同的附件数量和总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        contract.setExtNum(contract.getExtNum() + 1);

        // 7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 修改
     *
     * @param extCproduct
     */
    @Override
    public void update(ExtCproduct extCproduct) {

        // 1.获取修改之前的金额
        ExtCproduct oldExp = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        Double oldAmount = oldExp.getAmount();

        // 2.计算修改之后的总金额
        Double amount = 0d;
        Integer cnumber = extCproduct.getCnumber();
        Double price = extCproduct.getPrice();

        // 2.1 判断是否为null
        if (cnumber != null && price != null) {
            amount = cnumber * price;
        }

        // 3.设置附件修改之后的总金额
        extCproduct.setAmount(amount);

        // 4.更新附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);

        // 5.根据附件所属的购销合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());

        // 6.计算购销合同的总金额  总金额 - 修改之前的金额 + 修改之后的金额
        contract.setTotalAmount(contract.getTotalAmount() - oldAmount + amount);

        // 7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除部门
     *
     * @param id 附件id
     */
    @Override
    public void delete(String id) {

        // 根据附件id查询附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);

        // 查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());

        // 修改购销合同的总金额和附件数量
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        contract.setExtNum(contract.getExtNum() - 1);

        // 删除附件
        extCproductDao.deleteByPrimaryKey(id);

        // 更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }
}
