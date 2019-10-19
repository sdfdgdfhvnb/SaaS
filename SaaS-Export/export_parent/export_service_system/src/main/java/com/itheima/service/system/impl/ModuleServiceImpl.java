package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.ModuleDao;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 模块service接口实现类
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    // 注入dao
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private UserDao userDao;

    // 模块分页
    public PageInfo<Module> findByPage(Integer pageNum, Integer pageSize) {

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 调用service
        List<Module> moduleList = moduleDao.findAll();

        // 返回
        return new PageInfo<Module>(moduleList);
    }

    //根据id查询模块
    public Module findById(String moduleId) {
        return moduleDao.findById(moduleId);
    }

    //根据id删除
    public void delete(String moduleId) {
        moduleDao.delete(moduleId);
    }

    //添加
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    //更新
    public void update(Module module) {
        moduleDao.update(module);
    }

    //查询全部
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    // 根据角色id查询拥有的权限
    public List<Module> findModuleByRoleId(String roleId) {
        return moduleDao.findModuleByRoleId(roleId);
    }

    @Override
    public List<Module> findModuleByUserId(String id) {
        // 根据用户id查询权限列表

        // 2.根据用户的id查询用户，得到此用户的degree，然后做出下一步的处理（在service实现类完成）
        User user = userDao.findById(id);
        Integer degree = user.getDegree();

        if (degree == 0) {

            //2.1 如果degree为0或者1，之前查询module表得到权限列表
            return moduleDao.findByBelong(0);

        } else if (degree == 1) {

            //2.2 如果degree为其他数字，则根据id取查询用户列表
            return moduleDao.findByBelong(1);
        } else {

            return moduleDao.findModuleByUserId(id);

        }
    }
}
