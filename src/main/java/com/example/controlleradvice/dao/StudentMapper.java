package com.example.controlleradvice.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.controlleradvice.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author lqh
 * @since 2019-09-10
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}