package com.mz.auth.mapper;

import com.mz.auth.entity.User;
import com.mz.auth.query.UserQuery;
import lombok.ToString;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 9:00
 */
@Mapper
public interface UserMapper {

    //查询所有用户
    @Select("select * from t_user")
    List<User> queryAll();

    //查询总的条数
    Long queryTotal(UserQuery userQuery);

    //查询当前页的数据
    List<User> queryData(UserQuery userQuery);

    //保存用户
    @Insert("insert into t_user(username,password,email,sex,tel,createTime,type)" +
            "values(#{username},#{password},#{email},#{sex},#{tel},#{createTime},#{type})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addUser(User user);

    //根据用户id 删除数据
    @Delete("delete from t_user where id=#{id}")
    void deleteUser(Long id);


    //批量删除 delete from t_user where id in(1,2,3)
    @Delete("<script>" +
            "delete from t_user where id in" +
            "<foreach collection='array' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>"+
            "</script>")
    void deleleBatchUser(Long[] ids);



    //保存用户角色
    @Insert("<script>" +
            "insert into t_user_role (userid,roleid) values" +
            "<foreach collection='list' item='item'  separator=',' >" +
            "(#{item.userId},#{item.roleId})" +
            "</foreach>"+
            "</script>")
    void saveUserRole(List<Map> paramList);

    //删除用户角色 中间表
    @Delete("delete from t_user_role where userid=#{id}")
    void deleteUserRole(Long userId);

    //根据用户名去查询用户的方法
    @Select("select * from t_user where username=#{username}")
    User findUserByUsername(String username);

    //根据用户id更新头像
    @Update("update t_user set headImg = #{headImg} where id=#{id}")
    void updateHeadImgByUser(User user);
}
