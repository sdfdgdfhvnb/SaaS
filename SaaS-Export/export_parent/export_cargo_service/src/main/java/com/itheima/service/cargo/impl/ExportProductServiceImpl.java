package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ExportProductDao;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;
import com.itheima.service.cargo.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 出口货物表实现类
 */
@Service
public class ExportProductServiceImpl implements ExportProductService {

    // 注入dao
    @Autowired
    private ExportProductDao exportProductDao;

    @Override
    public PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        List<ExportProduct> exportProductList = exportProductDao.selectByExample(exportProductExample);

        return new PageInfo<>(exportProductList);
    }

    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductDao.selectByExample(example);
    }

    @Override
    public ExportProduct findById(String id) {
        return exportProductDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ExportProduct exportProduct) {
        exportProductDao.insertSelective(exportProduct);
    }

    @Override
    public void update(ExportProduct exportProduct) {
        exportProductDao.updateByPrimaryKeySelective(exportProduct);
    }

    @Override
    public void delete(String id) {
        exportProductDao.deleteByPrimaryKey(id);
    }

}
