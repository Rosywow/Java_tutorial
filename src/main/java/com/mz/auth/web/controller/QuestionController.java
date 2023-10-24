package com.mz.auth.web.controller;

import com.mz.auth.entity.Question;
import com.mz.auth.query.QuestionQuery;
import com.mz.auth.service.QuestionService;
import com.mz.auth.util.MzResult;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    //跳转题库列表页
    @RequestMapping("/question/index")
    public String index(){
        return "views/question/question_list";
    }

    //跳转 新增问题页面
    @RequestMapping("/question/gotoAddQuestion")
    public String gotoAddQuestion(){
        return "views/question/question_add";
    }
    //跳转题库的修改页面
    @RequestMapping("/question/gotoEditQuestion/{id}")
    public String index(@PathVariable("id") Long id, Model model){
        //Long id, Model model即把id存进model里面
        model.addAttribute("qid",id);
        return "views/question/question_edit";
    }


    //分页查询数据
    @GetMapping("/question/listpage")
    @ResponseBody
    public PageList listPage(QuestionQuery questionQuery){
        return questionService.listPage(questionQuery);
    }

    //新增题目
    @PostMapping("/question/addQuestion")
    @ResponseBody
    public MzResult addQuestion(@RequestBody Question question){
        try {
            questionService.addQuestion(question);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    //根据问题id查询的问题
    @PostMapping("/question/queryQuestionByQid")
    @ResponseBody
    public Question queryQuestionByQid( Long qid){
        return questionService.queryQuestionByQid(qid);
    }

    //修改保存问题方法
    @PostMapping("/question/editQuestion")
    @ResponseBody
    public MzResult editQuestion(@RequestBody Question question){
        try {
            questionService.editQuestion(question);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }
    //删除问题方法
    @GetMapping("/question/deleteQuestion")
    @ResponseBody
    public MzResult deleteQuestion(Long id){//id
        try {
            questionService.deleteQuestion(id);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }
}