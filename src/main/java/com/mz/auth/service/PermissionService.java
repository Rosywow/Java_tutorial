package com.mz.auth.service;

import com.mz.auth.entity.Permission;

import java.util.List;

/**
 * @author cr
 * @date 2023/2/13
 * @description
 */
public interface PermissionService {
    //查询所有权限及对应菜单
    public List<Permission> listAllPermission();

    //添加页面的按钮权限
    void addBtnPermission(Permission permission);

    //修改页面权限按钮
    void editBtnPermission(Permission permission);

    //根据id删除按钮权限
    void deleteBtnPermission(Long id);

    List<Permission> findAllPermission();
    //通过用户id查询用户权限
    List<Permission> listPermissionsByUserid(Long id);
}
