package com.example.controlleradvice.Controller;


import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class UserController {

    /**
     *
     */
    @RequestMapping("/test")
    public String testLeaf(Model model){
    model.addAttribute("name","黑马");
    //返回test.html页面
    return "test";
    }
    @RequestMapping("/noAuth")
    public String tonoAuth(){
        return "noAuth";
    }



    @RequestMapping("/add")
    public String add(){
        return "/user/add";
    }
    @RequestMapping("/toupdate")
    public String update(){
        return "/user/update";
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String username,String password,Model model){
        /**
         * 使用Shiro编写认证操作
         */
        //1 获取subject
        Subject subject= SecurityUtils.getSubject();
        //2 封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        //执行登录方法   如果不能成功 会抛出异常
        try{
            subject.login(token);
            //登录成功 跳转test页面
            return "redirect:/test";
        }catch (UnknownAccountException e){
            //登录失败 用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }

    }
}
