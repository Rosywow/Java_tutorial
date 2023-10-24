package com.mz.auth.mapper;

import com.mz.auth.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

    //注册学生
    @Insert("insert into t_student(username,password,tel,email,stuNum,createTime,nickName)" +
            " values(#{username},#{password},#{tel},#{email},#{stuNum},#{createTime},#{nickName})")
    void regStu(Student student);

    //学生登录
    @Select("select * from t_student where username =#{username} and password=#{password}")
    Student login(Student student);
}