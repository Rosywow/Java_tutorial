package com.mz.auth.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StuPaperQuestionVo {
    private Long id;//试卷id
    private String name;//试卷名称
    /*
    * 问题集合列表 对象
    * */
    private List<StuQuestionQueryVO> stuQuestionsList = new ArrayList();
}