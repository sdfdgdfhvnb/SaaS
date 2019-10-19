package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;

import java.util.List;

/**
 * 用户service接口
 */
public interface UserService {

    // 分页查询
    PageInfo<User> findByPage(String companyId, Integer pageNum, Integer pageSize);

    // 根据企业id查询所有用户
    List<User> findAll(String companyId);

    // 根据id查询用户
    User findById(String id);

    // 添加用户
    void save(User user);

    // 修改用户
    void update(User user);

    // 根据id删除用户
    boolean delete(String id);

    // 保存用户角色
    void changeRole(String userId, String[] roleIds);

    // 根据email查询用户
    User findByEmail(String email);
}
