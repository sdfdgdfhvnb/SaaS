package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.service.cargo.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {


    // 注入dao
    @Autowired
    private ContractDao contractDao;

    /**
     * 分页查询
     *
     * @param contractExample 分页查询的参数
     * @param pageNum         当前页
     * @param pageSize        页大小
     * @return
     */
    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Contract> list = contractDao.selectByExample(contractExample);

        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @param contractExample
     */
    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        return contractDao.selectByExample(contractExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param contract
     */
    @Override
    public void save(Contract contract) {

        // 设置id
        contract.setId(UUID.randomUUID().toString());
        // 设置总金额为0
        contract.setTotalAmount(0d);
        // 设置货物数附件数默认为0
        contract.setProNum(0);
        contract.setExtNum(0);
        // 创建时间
        contract.setCreateTime(new Date());
        // 默认状态为草稿
        contract.setState(0);

        contractDao.insertSelective(contract);
    }

    /**
     * 修改
     *
     * @param contract
     */
    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除部门
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Contract> selectByDeptId(String deptId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contract> list = contractDao.selectByDeptId(deptId);
        return new  PageInfo<>(list);
    }
}
