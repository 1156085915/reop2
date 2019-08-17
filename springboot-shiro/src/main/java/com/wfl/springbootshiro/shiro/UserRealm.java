package com.wfl.springbootshiro.shiro;


import com.wfl.springbootshiro.domain.User;
import com.wfl.springbootshiro.serivce.UserSerivce;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/***
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserSerivce userSerivce;
    /**
     * 执行授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();

        //到数据库查询当前登录用户的授权信息
        //获取当前登录的用户
        Subject subject= SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        User dbUser=userSerivce.findByUserId(user.getId());
        info.addStringPermission(dbUser.getPerms());
        return info;
    }

    /**
     * 执行认证逻辑
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //编写shiro认证逻辑，判断用户名和密码是否正确
        UsernamePasswordToken userToken=(UsernamePasswordToken) token;

        User user=userSerivce.findByUser(userToken.getUsername());

        if(user==null){
            //用户名不存在
            return null;//shiro底层会抛出UnknownAccountException
        }

            //判断密码
            return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
