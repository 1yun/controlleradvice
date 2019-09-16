package com.example.controlleradvice.Controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.example.controlleradvice.common.utils.TokenUtil;
import com.example.controlleradvice.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lqh
 * @since 2019-09-10
 */
@RestController
public class StudentController {
    @Autowired
    private IService service;

    @PostMapping("/login1")
    public Map<String,Object> login(String username,String password){

        Map<String,Object> map = new HashMap<>();
        EntityWrapper<Student> ew=new EntityWrapper<>();
        ew.eq("stu_name",username);
        ew.eq("password",password);
        int i = service.selectCount(ew);
        if(i>0){
            String token= TokenUtil
                    .sign(Student.builder().stuName(username).password(password).build());
            if(token!=null){
                map.put("code","202");
                map.put("msg","认证成功");
                map.put("token",token);
                return map;
            }
        }
        map.put("code", "1000");
        map.put("message", "认证失败");
        return map;
    }

    @PostMapping("/getList")
    public List<Student> getList(){

        List list = service.selectList(null);
        return list;

    }
}
