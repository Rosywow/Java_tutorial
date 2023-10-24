package com.mz.auth.service;


import com.mz.auth.entity.User;
import com.mz.auth.query.UserQuery;
import com.mz.auth.util.PageList;

import java.util.Map;


/**
 * @description:
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 9:01
 */

public interface UserService {
    //分页查询用户
    PageList listPage(UserQuery userQuery);

    //保存用户方法
    Long addUser(User user);
    //根据ID删除单条
    void deleteUser(Long id);
    //批量删除
    void deleteBatchUser(Long[] ids);
    //保存用户角色
    void saveUserRole(Map paramMap);
    //据用户名查询用户
    User findUserByUsername(String username);

    //老师注册
    Long addTeacher(User user);
    //根据用户Id修改头像图片
    void updateHeadImgByUser(User user);
}
