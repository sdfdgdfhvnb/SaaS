package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.DeptDao;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 部门接口实现类
 */
@Service
public class DeptServiceImpl implements DeptService {

    // 注入dao
    @Autowired
    private DeptDao deptDao;

    /**
     * 分页查询
     *
     * @param companyId 所属企业id
     * @param pageNum   当前页
     * @param pageSize  页面数据的条数
     * @return
     */
    public PageInfo<Dept> findByPage(String companyId, Integer pageNum, Integer pageSize) {

        // 开始分页
        PageHelper.startPage(pageNum, pageSize);

        // 封装数据
        return new PageInfo<>(deptDao.findAll(companyId));
    }

    /**
     * 根据部门id查询部门
     *
     * @param id
     * @return
     */
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    /**
     * 查询所有的部门
     *
     * @param companyId
     * @return
     */
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    /**
     * 保存部门
     *
     * @param dept
     */
    @Override
    public void save(Dept dept) {
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    /**
     * 修改部门
     *
     * @param dept
     */
    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    /**
     * 根据部门id删除部门
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        // 删除部门，先根据删除的部门id查询看是否有子部门，如果有就不能删除。给与提示。
        // SELECT * FROM pe_dept WHERE parent_id='100'  -- 有子部门，不能删除
        // DELETE FROM pe_dept WHERE dept_id='100'

        // 1.线看要删除的部门下是否有子部门
        List<Dept> deptList = deptDao.findByParentId(id);

        // 判断
        if (deptList == null || deptList.size() == 0) {

            // 当前部门没有子部门，可以删除
            deptDao.delete(id);
            return true;
        }

        // 4.当前部门有子部门,不能删除
        return false;
    }
}
