package com.mz.auth.web.controller;

import com.mz.auth.entity.ScoreDetail;
import com.mz.auth.entity.User;
import com.mz.auth.query.ScoreDetailQuery;
import com.mz.auth.service.ScoreDetailService;
import com.mz.auth.service.UserService;
import com.mz.auth.util.MzResult;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: TeacherController 老师控制器  注册
 */
@Controller
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreDetailService scoreDetailService;
    //注册老师
    @PostMapping("/teacher/regTeacher")
    @ResponseBody
    public MzResult regTeacher(User user){
        try {
            Long userid = userService.addTeacher(user);
            //进入MzResult类
            return MzResult.ok().put("userid",userid); 
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    //跳转老师阅卷页面
    @GetMapping("/teacher/paperExamRecord")
    public String paperExamRecord(){
        return "views/teacher/paper_check";
    }

    //老师阅卷题目查询
    @GetMapping("/teacher/paperExamlistpage")
    @ResponseBody
    public PageList listPaperExamPage(ScoreDetailQuery scoreDetailQuery){
        return scoreDetailService.listPage(scoreDetailQuery);
    }

    //老师阅卷操作修改得分
    @PostMapping("/teacher/updateJdtScore")
    @ResponseBody
    public MzResult updateJdtScore(ScoreDetail scoreDetail){
        try {
            scoreDetailService.updateJdtScore(scoreDetail);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }
}
