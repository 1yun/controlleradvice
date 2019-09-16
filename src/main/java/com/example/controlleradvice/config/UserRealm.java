package com.example.controlleradvice.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.controlleradvice.entity.Student;
import com.example.controlleradvice.service.IStudentService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//自定义Realm

public class UserRealm  extends AuthorizingRealm {

    @Autowired
    private IStudentService studentService;
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        //添加资源授权字符串
        //info.addStringPermission("user:add");
        //到数据库中查询当前登录用户授权字符串
        Subject subject= SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        Student s=(Student)subject.getPrincipal();

        Student dbs=studentService.selectById(s.getId());
        System.out.println(dbs);
        info.addStringPermission(dbs.getPerms());
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证逻辑");
        //假设数据库中用户名和密码
        //String name="yzy";
        //String password="123456";

        //编写shiro判断逻辑  判断用户名和密码
        //1 判断用户名
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;

        EntityWrapper ew=new EntityWrapper<>();
        ew.eq("stu_name",token.getUsername());
        Student student = studentService.selectOne(ew);
        if(student==null){
            //用户名不存在
            return null;// shiro 底层会抛出UnknowAccountException
        }
        //判断密码
        return new SimpleAuthenticationInfo(student,student.getPassword(),"");

    }
}
