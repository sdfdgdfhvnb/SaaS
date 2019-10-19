package com.itheima.web.shiro;

import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Shiro认证类
 *
 * shiro认证常见的错误:
 * 1.UnknownAccountException 用户名错误
 * 2.IncorrectCredentialsException 密码错误
 * 数据库中的密码没有加密：Caused by: java.lang.IllegalArgumentException: Odd number of characters.
 */
public class AuthRealm extends AuthorizingRealm {

    // 注入userService和moduleService
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        /*查询用户的权限并且返回*/
        // 1.获取登录后的用户对象
        User user = (User) principals.getPrimaryPrincipal();

        // 2.查询用户的权限
        List<Module> moduleList = moduleService.findModuleByUserId(user.getId());

        // 3.返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 4.遍历
        if (moduleList != null && moduleList.size() > 0) {
            for (Module module : moduleList) {
                info.addStringPermission(module.getName());
            }
        }

        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        // 1.获取用户名
        String email = (String) token.getPrincipal();

        // 2.查询数据库
        User user = userService.findByEmail(email);

        // 3.判断user是否为null
        if (user == null) {
            // 说明用户不存在，报错：UnknownAccountException
            return null;
        }

        // 4.返回
        // 参数1: 认证后的身份对象 通过subject.getPrincipal()获取的就是这里的参数1
        // 参数2: 数据库中正确的密码(shiro自动校验,校验失败: IncorrectCredentialsException)
        // 参数3: realm名称,可以随便写,确保唯一 getName()获取shiro默认的realm名称
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());

        return info;
    }
}
