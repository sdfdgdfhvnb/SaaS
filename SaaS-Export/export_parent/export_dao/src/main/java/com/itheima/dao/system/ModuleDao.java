package com.itheima.dao.system;

import com.itheima.domain.system.Module;

import java.util.List;

/**
 * 模块接口
 */
public interface ModuleDao {

    //根据id查询模块
    Module findById(String moduleId);

    //根据id删除
    void delete(String moduleId);

    //添加
    void save(Module module);

    //更新
    void update(Module module);

    //查询全部
    List<Module> findAll();

    // 根据角色id查询拥有的权限
    List<Module> findModuleByRoleId(String roleId);

    // 根据belong查询权限列表
    List<Module> findByBelong(int belong);

    // 根据用户id查询权限列表
    List<Module> findModuleByUserId(String id);
}
