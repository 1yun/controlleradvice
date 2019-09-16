package com.example.controlleradvice.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * Subject 用户主体（把操作交给SecurityManager）
     * SecurityManager  安全管理器  关联（Realm）
     * Realm shiro 连接数据的桥梁
     */
    @Bean
    public ShiroFilterFactoryBean getFilterFactory(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**添加Shiro内置过滤器
         * Shiro内置过滤器 可以实现相关权限相关的拦截器
         * 常用的拦截器
         *  anon：无需认证（登录）可以访问
         *  authc：必须认证才可以访问
         *  user：如果使用rememberMe的功能可以直接访问
         *  perms：该资源必须得到资源权限才可以访问
         *  role：该资源必须得到角色权限才可以访问
         *
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
         /*
        认证顺序是从上往下执行。
         */
         /*filterMap.put("/add","authc");
         filterMap.put("/toupdate","authc");*/
        filterMap.put("/test","anon");
        //执行login页面
        filterMap.put("/login","anon");
        //jwt界面
        filterMap.put("/login1","anon");
        filterMap.put("/getList","anon");
        //授权过滤器
        //注意 当前授权拦截后  shiro会自动跳转到未授权页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/toupdate","perms[user:update]");

        filterMap.put("/*","authc");


         //设置调整的登录页面
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }


    /**
     * @Qualifier根据名字获取
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 配置shiroDialect 用户thyeleaf和shiro标签组合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
