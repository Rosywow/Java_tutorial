package com.mz.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Question {//对应exam_questionbank数据库表
    //问题主键id
    private Long id;
    private String questionTitle;//问题题目
    private String questionAnswer;//问题答案
    //问题类型id  1 选择题 2填空题 3判断题 4简答题
    private Long q_typeid;
    /**问题类型对象  
     * exam_QuestionType 数据库表  的对象为 questionType
     */
    private QuestionType questionType;
    private Long status;//问题的状态 0 正常 1 停用
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**选择题选项 对象
     * exam_xzt_options数据库表 对象为 questionXztOptions
     */
    private QuestionXztOptions questionXztOptions;
    private Long creatorId;//创建者id
    /**创建者 对象 exam_t_user数据库表 对象为user
     */
    private User user;//创建者
    private Integer grade;//分数
}