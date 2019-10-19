package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * 部门service接口
 */
public interface DeptService {

    /**
     * 分页查询
     *
     * @param companyId 所属企业id
     * @param pageNum   当前页
     * @param pageSize  页面数据的条数
     * @return
     */
    PageInfo<Dept> findByPage(String companyId, Integer pageNum, Integer pageSize);

    /**
     * 根据部门id查询部门
     *
     * @param id
     * @return
     */
    Dept findById(String id);


    /**
     * 查询所有的部门
     *
     * @param companyId
     * @return
     */
    List<Dept> findAll(String companyId);

    /**
     * 保存部门
     *
     * @param dept
     */
    void save(Dept dept);

    /**
     * 修改部门
     *
     * @param dept
     */
    void update(Dept dept);

    /**
     * 根据部门id删除部门
     *
     * @param id
     * @return
     */
    boolean delete(String id);
}
