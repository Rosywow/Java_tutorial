package com.mz.auth.web.controller.front;


import com.mz.auth.entity.ScoreDetail;
import com.mz.auth.entity.Student;
import com.mz.auth.service.PaperService;

import com.mz.auth.service.ScoreDetailService;
import com.mz.auth.service.StudentService;
import com.mz.auth.util.MzResult;
import com.mz.auth.vo.StuScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description: StudentController
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private ScoreDetailService scoreDetailService;


    //注册学生账号信息
    @RequestMapping("/stu/regStu")
    @ResponseBody
    public MzResult regStu(Student student){
        try {
            studentService.regStu(student);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

     //学生登录
    @RequestMapping("/stu/login")
    @ResponseBody
    public MzResult login(Student student, HttpSession session){

        try {
            //判断验证码是否正确]
            String code = (String)session.getAttribute("code");
            if(!code.equals(student.getUsercode())){
                return MzResult.error("验证码输入有错误!");
            }
            Student stu =  studentService.login(student);
             if(stu==null){
                 return MzResult.error("用户名或者密码错误");
             }else{
                 session.setAttribute("stuUser",stu);
                 return MzResult.ok();
             }
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 退出
     *  logOut
     */
    @RequestMapping("/stu/logOut")
    public String logout(HttpSession session){
        session.removeAttribute("stuUser");
        return "front/loginIndex";
    }


    //查询所有的科目
    @RequestMapping("/stu/queryAllScorePage")
    public String queryAllScorePage(Model model, HttpSession session){
        Student stuUser = (Student)session.getAttribute("stuUser");
        if(stuUser == null){//如果没登录，重定向到登录页面
            return "redirect:/front/login";
        }
        //查询该学生所有考试 总分
        List<StuScoreVO> result =  scoreDetailService.queryFrontAllStuScore(stuUser.getId());

        model.addAttribute("allScores",  result);
        return "front/queryAllScoreIndex";
    }

    //回顾试卷：查看学生考试试卷的答题情况
    @RequestMapping("/stu/examDetailRecords")
    public String queryFrontExamDetailRecords(Model model,Long paperId,Long stuId){
        //实例化 ScoreDetail考试记录明细的实体类对象
        ScoreDetail scoreDetail = new ScoreDetail();
        scoreDetail.setStuId(stuId);
        scoreDetail.setPaperId(paperId);
        //把queryPaperDetail（）方法查询出来的 存进stuPaperQuestionVO对象，等待前台调用
        model.addAttribute("stuPaperQuestionVO", scoreDetailService.queryPaperDetail(scoreDetail));
        //跳转页面
        return "front/examDetailIndex";
    }
}
