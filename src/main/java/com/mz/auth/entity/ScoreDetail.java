package com.mz.auth.entity;

import lombok.Data;

/**
 * @description: ScoreDetail考试记录明细的实体类 对应考试记录表exam_ScoreDetail
 */
@Data
public class ScoreDetail {
    //记录id
    private Long id;//
    private Long stuId;//学生id
    private Long paperId;//试卷id
    private Long questionId;//问题id
    private String questionTitle;//问题标题
    private Long q_typeid;//问题类型
    private String questionAnswer;//学生答案
    private String questionScore;//问题分数
    private String correntAnswer;//正确答案
    private String correntScore;//学生分数
    /**
     * 学生对象  实体类里面有学生的名字
     */
    private Student student;
    /**
     * 试卷对象     实体类里面有试卷的名字
     */
    private Paper paper;
}
