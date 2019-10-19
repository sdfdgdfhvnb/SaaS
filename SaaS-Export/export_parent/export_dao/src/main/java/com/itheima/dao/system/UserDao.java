package com.itheima.dao.system;

import com.itheima.domain.system.Module;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户接口
 */
public interface UserDao {

    // 根据企业id查询所有用户
    List<User> findAll(String companyId);

    // 根据id查询用户
    User findById(String id);

    // 添加用户
    void save(User user);

    // 修改用户
    void update(User user);

    // 根据id删除用户
    void delete(String id);

    // 根据用户id查询用户角色中间表
    Long findUserRoleByUserId(String id);

    // 根据用户id删除角色
    void deleteRoleByUserId(String userId);

    // 添加角色
    void saveRoleByUserId(@Param("userId") String userId, @Param("roleId") String roleId);

    // 根据email查询用户
    User findByEmail(String email);
}
