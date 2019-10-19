package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.company.CompanyDao;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * company的service实现类
 */
@Service(timeout = 100000) // 超时时间设置为100秒
public class CompanyServiceImpl implements CompanyService {
    // 定义dao接口对象
    @Autowired
    private CompanyDao companyDao;

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return 返回分装分页参数的PageInfo对象
     */
    public PageInfo<Company> findByPage(int pageNum, int pageSize) {

        // 1.开启dao查询的分页支持
        PageHelper.startPage(pageNum, pageSize);

        // 2.调用dao
        List<Company> list = companyDao.findAll();

        // 3。封装分页数据
        return new PageInfo<>(list);
    }

    /**
     * 查询企业
     *
     * @return 返回所有租用企业
     */
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    /**
     * 保存用户
     *
     * @param company
     */
    @Override
    public void save(Company company) {
        // 设置主键值
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }

    /**
     * 修改用户
     *
     * @param company
     */
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    /**
     * 根据企业id查询企业
     *
     * @param id
     * @return
     */
    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    /**
     * 根据id删除企业
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }
}
