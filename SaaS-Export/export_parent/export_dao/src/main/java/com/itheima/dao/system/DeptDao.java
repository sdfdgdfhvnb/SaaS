package com.itheima.dao.system;

import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * 部门dao接口
 */
public interface DeptDao {

    /**
     * 查询全部部门(根据企业id查询)
     * @param companyId
     * @return
     */
    List<Dept> findAll(String companyId);

    /**
     * 根据部门id查询部门
     * @param id
     * @return
     */
    Dept findById(String id);

    /**
     * 保存部门
     * @param dept
     */
    void save(Dept dept);

    /**
     * 修改部门
     * @param dept
     */
    void update(Dept dept);

    /**
     * 查询部门的子部门
     * @param id
     * @return
     */
    List<Dept> findByParentId(String id);

    /**
     * 根据部门id删除部门
     * @param id
     */
    void delete(String id);
}
