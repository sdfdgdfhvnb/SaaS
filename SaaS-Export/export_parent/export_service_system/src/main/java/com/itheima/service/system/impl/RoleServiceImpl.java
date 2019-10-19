package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.RoleDao;
import com.itheima.domain.system.Role;
import com.itheima.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 角色service接口实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    // 注入dao
    @Autowired
    private RoleDao roleDao;

    // 分页数据
    public PageInfo<Role> findByPage(String companyId, Integer pageNum, Integer pageSize) {

        // 开始分页
        PageHelper.startPage(pageNum, pageSize);

        // 根据企业id查询所有角色
        List<Role> roleList = roleDao.findAll(companyId);

        // 返回数据
        return new PageInfo<Role>(roleList);
    }

    // 根据id查询角色
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    // 根据企业id查询全部角色
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    // 添加角色
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    // 修改用户
    public void update(Role role) {
        roleDao.update(role);
    }

    // 根据id删除用户
    public void delete(String id) {
        roleDao.delete(id);
    }

    // 给角色分配权限
    public void updateRoleModule(String roleId, String moduleIds) {
        //-- 1）解除角色权限关系
        //DELETE FROM pe_role_module WHERE role_id=''
        //-- 2) 角色添加权限
        //INSERT INTO pe_role_module(role_id,module_id) VALUES('','')

        // 1.解除角色权限
        roleDao.deleteRoleModuleByRoleId(roleId);

        // 2.角色添加权限
        if (moduleIds != null && !"".equals(moduleIds)) {

            // 2.1 分割字符串
            String[] array = moduleIds.split(",");

            // 2.2 判断
            if (array != null && array.length > 0) {

                for (String module : array) {
                    roleDao.saveRoleModule(roleId, module);
                }
            }
        }
    }

    // 根据用户id查询此用户拥有的角色
    public List<Role> findRoleByUserId(String id) {
        return roleDao.findRoleByUserId(id);
    }
}
