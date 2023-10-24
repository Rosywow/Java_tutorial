package com.mz.auth.service.impl;

import com.mz.auth.entity.Permission;
import com.mz.auth.mapper.PermissionMapper;
import com.mz.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 14:56
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    //查询所有的权限和对应的菜单
    @Override
    public List<Permission> listAllPermission() {
        return permissionMapper.listAllPermission();
    }
    /**
    //查询所有的权限
    @Override
    public List<Permission> findAllPermission() {
        return permissionMapper.findAllPermission();
    }




    //通过用户Id查询权限
    @Override
    public List<Permission> listPermissionsByUserid(Long userid) {
        return permissionMapper.listPermissionsByUserid(userid);
    }
    */

    //添加页面按钮权限
    @Override
    public void addBtnPermission(Permission permission) {
        permissionMapper.addBtnPermission(permission);
    }

    //修改页面 按钮权限功能
    @Override
    public void editBtnPermission(Permission permission) {
        permissionMapper.editBtnPermission(permission);
    }

    //根据id删除按钮权限
    @Override
    public void deleteBtnPermission(Long id) {
        permissionMapper.deleteBtnPermission(id);
    }

    @Override
    public List<Permission> findAllPermission() {
        return permissionMapper.findAllPermission();
    }

    @Override
    public List<Permission> listPermissionsByUserid(Long id) {
        return permissionMapper.listPermissionsByUserid(id);
    }

}
