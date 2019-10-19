package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.User;
import com.itheima.service.system.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 用户service实现类
 */
@Service
public class UserServiceImpl implements UserService {

    // 注入dao
    @Autowired
    private UserDao userDao;

    // 分页查询
    public PageInfo<User> findByPage(String companyId, Integer pageNum, Integer pageSize) {

        // 1.调用start方法
        PageHelper.startPage(pageNum, pageSize);

        // 2.查询全部列表
        List<User> userList = userDao.findAll(companyId);

        // 返回数据
        return new PageInfo<User>(userList);
    }

    // 根据企业id查询所有用户
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    // 根据id查询用户
    public User findById(String id) {
        return userDao.findById(id);
    }

    // 添加用户
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        if (user.getPassword() != null) {
            String encodePwd = new Md5Hash(user.getPassword(), user.getEmail()).toString();
            user.setPassword(encodePwd);
        }
        userDao.save(user);
    }

    // 修改用户
    public void update(User user) {
        userDao.update(user);
    }

    // 根据id删除用户
    public boolean delete(String id) {

        //-- 先根据删除的用户id查询用户角色中间表
        //SELECT COUNT(1) FROM pe_role_user WHERE user_id='1'
        //-- 如果没有查到，可以删除
        //DELETE FROM pe_user WHERE user_id='1'
        Long count = userDao.findUserRoleByUserId(id);

        if (count == 0) {
            // 可以删除
            userDao.delete(id);
            return true;
        }

        return false;
    }

    // 保存用户角色
    public void changeRole(String userId, String[] roleIds) {
        // 在service实现类中处理：
        // 根据前端传过来的用户id查询pe_user_role，先删除所有的角色
        // 之后再向该用户添加权限（根据角色数组）
        // -- 1) 先解除用户角色关系
        //DELETE FROM pe_role_user WHERE user_id='1'
        //-- 2) 给用户添加角色
        //INSERT INTO pe_role_user(user_id,role_id)VALUES('','')

        // 根据用户id删除角色,不需要返回值
        userDao.deleteRoleByUserId(userId);

        // 给用户添加角色
        if (roleIds != null && roleIds.length > 0) {

            // 遍历数组
            for (String roleId : roleIds) {

                // 添加角色
                userDao.saveRoleByUserId(userId, roleId);
            }
        }
    }

    // 根据email查询用户
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}
