package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.company.Company;

import java.util.List;

/**
 * 对外发布服务的接口
 * company的service接口
 */
public interface CompanyService {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return 返回分装分页参数的PageInfo对象
     */
    PageInfo<Company> findByPage(int pageNum, int pageSize);

    /**
     * 查询企业
     *
     * @return 返回所有租用企业
     */
    List<Company> findAll();

    /**
     * 保存用户
     *
     * @param company
     */
    void save(Company company);

    /**
     * 修改用户
     *
     * @param company
     */
    void update(Company company);

    /**
     * 根据企业id查询企业
     *
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 根据id删除企业
     *
     * @param id
     */
    void delete(String id);
}
