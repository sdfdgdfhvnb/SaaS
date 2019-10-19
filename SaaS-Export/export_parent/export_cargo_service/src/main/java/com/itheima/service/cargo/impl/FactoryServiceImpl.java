package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.FactoryDao;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 服务实现类
 */
@Service
public class FactoryServiceImpl implements FactoryService {

    // 注入dao
    @Autowired
    private FactoryDao factoryDao;

    /**
     * 分页查询
     *
     * @param factoryExample
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<Factory> findByPage(FactoryExample factoryExample, int pageNum, int pageSize) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        List<Factory> list = factoryDao.selectByExample(factoryExample);

        // 返回
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @param factoryExample
     */
    @Override
    public List<Factory> findAll(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param factory
     */
    @Override
    public void save(Factory factory) {

        // 设置主键
        factory.setId(UUID.randomUUID().toString());

        factoryDao.insertSelective(factory);
    }

    /**
     * 修改
     *
     * @param factory
     */
    @Override
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /**
     * 删除部门
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据工厂i名称查询id
     *
     * @param factoryName
     * @return
     */
    @Override
    public String findFactoryNameById(String factoryName) {

        // 构造条件: 工厂名称
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andFactoryNameEqualTo(factoryName);

        // 查询
        List<Factory> factoryList = factoryDao.selectByExample(factoryExample);
        return (factoryList != null && factoryList.size() > 0 ? factoryList.get(0).getId() : "");
    }
}
