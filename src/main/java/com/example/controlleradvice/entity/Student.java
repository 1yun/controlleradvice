package com.example.controlleradvice.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Builder;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lqh
 * @since 2019-09-10
 */
@TableName("tb_student")
@Builder

public class Student implements  Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("stu_name")
	private String stuName;
	@TableField("stu_number")
	private String stuNumber;
	private Integer age;

	private String password;
	private String perms;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getId() {
		return id;
	}

	public Student setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getStuName() {
		return stuName;
	}

	public Student setStuName(String stuName) {
		this.stuName = stuName;
		return this;
	}

	public String getStuNumber() {
		return stuNumber;
	}

	public Student setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
		return this;
	}

	public Integer getAge() {
		return age;
	}

	public Student setAge(Integer age) {
		this.age = age;
		return this;
	}

	public Student() {
	}

	public Student(Integer id, String stuName, String stuNumber, Integer age, String password, String perms) {
		this.id = id;
		this.stuName = stuName;
		this.stuNumber = stuNumber;
		this.age = age;
		this.password = password;
		this.perms = perms;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", stuName='" + stuName + '\'' +
				", stuNumber='" + stuNumber + '\'' +
				", age=" + age +
				", password='" + password + '\'' +
				", perms='" + perms + '\'' +
				'}';
	}
}
