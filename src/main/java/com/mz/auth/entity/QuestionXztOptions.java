package com.mz.auth.entity;

import lombok.Data;

@Data
public class QuestionXztOptions {
 
    private Long id;//主键id

    private String optionA;//选择题A选项
    private String optionB;//选择题B选项
    private String optionC;//选择题c选项
    private String optionD;//选择题d选项
    //问题编号 id
    private Long questionId;
}