package com.example.controlleradvice.Controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.controlleradvice.common.annotion.Idempotent;
import com.example.controlleradvice.common.annotion.sysLog;
import com.example.controlleradvice.common.service.ICacheService;
import com.example.controlleradvice.entity.Student;
import com.example.controlleradvice.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import javax.validation.Valid;

@RestController
public class demo {


    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ICacheService cacheService;
    @sysLog("/home")
    @RequestMapping("/home")
    public Student tohome(@ModelAttribute("author") String author) throws Exception{

        /*EntityWrapper ew=new EntityWrapper();
        ew.setEntity(new Student());
        ew.eq("stu_name","yang");
        int i = iStudentService.selectCount(ew);
        System.out.println(i);*/
        cacheService.add("session","1");
        Object obj = cacheService.get("session");
        System.out.println(obj.toString());


        return iStudentService.selectById(1);
    }

    @RequestMapping("/update")
    @Idempotent(value = "updatestudent",express = "#student.id+'-'+#student.age")
    public Student updateCityNameByBodyVo( Student student){
        iStudentService.updateById(student);
        return iStudentService.selectById(student.getId());
    }

}
