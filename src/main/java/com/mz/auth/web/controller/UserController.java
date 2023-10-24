package com.mz.auth.web.controller;


import com.mz.auth.entity.Role;
import com.mz.auth.entity.User;
import com.mz.auth.mapper.UserMapper;
import com.mz.auth.query.UserQuery;
import com.mz.auth.service.RoleService;
import com.mz.auth.service.UserService;
import com.mz.auth.util.MzResult;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 15:04
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转用户列表页
     */
    @GetMapping("/user/index")
    public String index(Model model){

        List<Role> roles = roleService.findAllRole();
        model.addAttribute("roles",roles);
        return "views/user/user_list";
    }

    //分页查询数据
    @GetMapping("/user/listpage")
    @ResponseBody
    public PageList listPage(UserQuery userQuery){
        return userService.listPage(userQuery);
    }
    //添加方法
    @PostMapping("/user/addUser")
    @ResponseBody
    public MzResult addUser(User user){
        try {
            Long userid = userService.addUser(user);
            return MzResult.ok().put("userid",userid);
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }
    //删除单条数据
    @GetMapping("/user/deleteUser")
    @ResponseBody
    public MzResult deleteUser(Long id){
        try {
            userService.deleteUser(id);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    //批量删除
    @PostMapping("/user/deleteBatchUser")
    @ResponseBody
    public MzResult deleteBatchUser(@RequestParam("ids[]") Long[] ids){
        try {
            userService.deleteBatchUser(ids);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }
    //保存用户角色 参数map集合中存放 用户iD 及角色数组
    @PostMapping("/user/saveUserRole")
    @ResponseBody
    public MzResult saveUserRole(@RequestBody Map paramMap){
        try {
            userService.saveUserRole(paramMap);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }


}
