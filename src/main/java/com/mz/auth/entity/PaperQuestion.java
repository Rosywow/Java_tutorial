package com.mz.auth.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaperQuestion {
  
    private Long id;//试卷问题的主键id
    private Long paperId;//试卷id
    private Long questionId;//问题id
    /**
     * 接收前台传递过来的 问题id 的数组列表
     */
    List<Question> questionIdsList = new ArrayList();
}