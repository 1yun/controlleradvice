package com.example.controlleradvice.service.imp;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.controlleradvice.dao.StudentMapper;
import com.example.controlleradvice.entity.Student;
import com.example.controlleradvice.service.IStudentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lqh
 * @since 2019-09-10
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    void page(){

        //Student> wrapper=new QueryWrapper<>();
       // this.baseMapper.delete();
    }
}
