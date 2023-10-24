package com.mz.auth.service;

import com.mz.auth.entity.Student;

public interface StudentService {

    //注册学生账号
    void regStu(Student student);

    //学生登录
    Student login(Student student);
}