package com.mz.auth.service;

import com.mz.auth.entity.Role;
import com.mz.auth.query.RoleQuery;
import com.mz.auth.util.PageList;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 14:54
 */
public interface RoleService {

    //分页查询
    PageList listPage(RoleQuery roleQuery);
    //保存角色
    void addRole(Role role);
    //修改保存角色
    void editRole(Role role);
    //根据id删除角色
    void deleteRole(Long id);
    //查询所有角色
    List<Role> findAllRole();

    void addRolePermission(Map paramMap);
    //通过用户Id查询用户角色
    List<Role> listRolesByUserid(Long id);
}
