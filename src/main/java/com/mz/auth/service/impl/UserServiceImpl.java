package com.mz.auth.service.impl;


import com.mz.auth.entity.User;
import com.mz.auth.mapper.UserMapper;
import com.mz.auth.query.UserQuery;
import com.mz.auth.service.UserService;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @description:
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 9:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //分页查询数据
    @Override
    public PageList listPage(UserQuery userQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = userMapper.queryTotal(userQuery);
        pageList.setTotal(total);
        //查询每页的数据
        List<User> users =  userMapper.queryData(userQuery);
        pageList.setRows(users);
        return pageList;
    }
    //添加用户
    @Override
    public Long addUser(User user) {
        user.setCreateTime(new Date());
        user.setType(1);
        //处理密码
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userMapper.addUser(user);
        return user.getId();
    }
    //根据用户id 删除数据
    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void deleteBatchUser(Long[] ids) {
        userMapper.deleleBatchUser(ids);
    }

    //保存用户角色
    @Override
    @Transactional
    public void saveUserRole(Map paramMap) {
        Long userId = Long.parseLong((String)paramMap.get("userId"));
        List rids =  (List)paramMap.get("roleIds");
        //（1） 先在中间表 删除用户角色
        userMapper.deleteUserRole(userId);
        // (2) 在插入中间表 批量插入操作
        List<Map> paramList = new ArrayList();
        rids.forEach(rid->{
            Map mp = new HashMap();
            mp.put("userId",userId);
            mp.put("roleId",rid);
            paramList.add(mp);
        });
        //批量保存中间表数据
        userMapper.saveUserRole(paramList);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public Long addTeacher(User user) {
        user.setType(2); //2代表老师
        user.setCreateTime(new Date());//注册时间
        //密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userMapper.addUser(user);
        return user.getId();//返回id
    }

    @Override
    public void updateHeadImgByUser(User user) {
        userMapper.updateHeadImgByUser(user);
    }
}
