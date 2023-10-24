package com.mz.auth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: RegController
 */
@Controller
public class RegController {
    /**
     * 跳转到注册页面
     */
    @RequestMapping("/goRegPage")
    public String gotoReg(){
        return "views/reg";
    }
}
