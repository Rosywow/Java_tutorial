package com.mz.auth.service.impl;

import com.mz.auth.entity.Student;
import com.mz.auth.mapper.StudentMapper;
import com.mz.auth.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    //学生注册
    @Override
    public void regStu(Student student) {
        studentMapper.regStu(student);
    }

    //学生登录
    @Override
    public Student login(Student student) {
        return studentMapper.login(student);
    }
}
