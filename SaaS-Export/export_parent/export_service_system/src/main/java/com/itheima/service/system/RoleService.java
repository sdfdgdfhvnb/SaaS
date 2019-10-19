package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;

/**
 * 角色service接口
 */
public interface RoleService {

    // 分页数据
    PageInfo<Role> findByPage(String companyId, Integer pageNum, Integer pageSize);

    // 根据id查询角色
    Role findById(String id);

    // 根据企业id查询全部角色
    List<Role> findAll(String companyId);

    // 添加角色
    void save(Role role);

    // 修改用户
    void update(Role role);

    // 根据id删除用户
    void delete(String id);

    // 给角色分配权限
    void updateRoleModule(String roleId, String moduleIds);

    // 根据用户id查询此用户拥有的角色
    List<Role> findRoleByUserId(String id);
}
