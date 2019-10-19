package com.itheima.dao.company;

import com.itheima.domain.company.Company;

import java.util.List;

/**
 * 公司dao接口
 */
public interface CompanyDao {

    /**
     * 查询企业
     * @return 返回所有租用企业
     */
    List<Company> findAll();

    /**
     * 保存用户
     * @param company
     */
    void save(Company company);

    /**
     * 修改用户
     * @param company
     */
    void update(Company company);

    /**
     * 根据企业id查询企业
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 根据id删除企业
     * @param id
     */
    void delete(String id);
}
