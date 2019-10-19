package com.itheima.dao.system;

import com.itheima.domain.system.Role;

import java.util.List;

/**
 * 角色接口
 */
public interface RoleDao {

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

    // 解除角色权限
    void deleteRoleModuleByRoleId(String roleId);

    // 角色添加权限
    void saveRoleModule(String roleId, String module);

    // 根据用户id查询此用户拥有的角色
    List<Role> findRoleByUserId(String id);
}
