package com.wfl.springbootshiro.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
*/
@Configuration
public class ShiroConfig {
    /**
     *  创建ShiroFilterFactoryBean
     *
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置shiro内置过滤器，可以实现一些常用的权限拦截
        /**
         * Shiro内置常用过滤器：
         *  anon： 无需认证(登录)可以访问
         *  authc：必须认证才能访问
         *  user： 如果使用rememberMe的功能才能访问
         *  perms：该资源必须授予资源权限才能访问 (资源授权)
         *  role： 该资源得到角色权限才能访问 (角色授权)
         */
        Map<String,String> filterMap= new LinkedHashMap<>();
        /**
         * filterMap.put("/user/*","authc"); //表示user文件夹下的所有页面都需要认证才能访问
         *
         *         filterMap.put("/add","authc");
         *         filterMap.put("/update","authc");
         */
        filterMap.put("/tologin","anon"); //放行tologin登录方法
        filterMap.put("/findByUser","anon");
        filterMap.put("/index","anon");
        //授权过滤器,当方法未授权时,会显示未授权的页面;只有赋予user:add权限才能访问
        filterMap.put("/add","perms[user:add]");
        //认证过滤器
        filterMap.put("/*","authc"); //所有方法都需要认证才能访问


        /**
         * 如果没认证shiro默认会自动跳转到login.jsp页面，这个路径也可以自定义配置
         * thymeleaf模板引擎必须都经过controller所以写controller的方法路径
         */
        shiroFilterFactoryBean.setLoginUrl("/login");
        /**
         * 配置：如果该方法未授权，则跳转到这个方法
         */
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     *  创建DefaultWebSecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     *  创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){

        return new UserRealm();
    }

    /**
     * 配置ShiroDialect 用于thymeleaf中使用Shiro标签
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
