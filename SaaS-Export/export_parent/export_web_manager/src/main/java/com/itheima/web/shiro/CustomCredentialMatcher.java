package com.itheima.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 自定义的凭证匹配器,对输入的密码进行加密,加盐
 */
public class CustomCredentialMatcher extends SimpleCredentialsMatcher {

    /**
     * 对用户输入的密码进行加密,加盐
     *
     * @param token 封装用户输入的信息
     * @param info  获取数据库中正确的密码
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        // 1.获取用户输入的用户名(邮箱)
        String email = (String) token.getPrincipal();

        // 2.获取用户输入的密码
        String inputPwd = new String((char[]) token.getCredentials());

        // 3.加密,加盐
        String encodePwd = new Md5Hash(inputPwd, email).toString();

        // 4.获取数据库中正确的密码
        String dbPwd = (String) info.getCredentials();

        return encodePwd.equals(dbPwd);
    }
}
