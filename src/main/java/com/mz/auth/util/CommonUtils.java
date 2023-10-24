package com.mz.auth.util;

import com.mz.auth.config.UserSecurity;
import com.mz.auth.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtils {
    /**
     * 得到登录认证用户的信息
     * @return
     */
    public static User getLoginUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserSecurity userSecurity = (auth !=null)?(UserSecurity)auth.getPrincipal():null;
        return userSecurity.getLoginUser();
    }
}