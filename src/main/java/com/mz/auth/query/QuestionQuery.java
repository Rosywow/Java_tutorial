package com.mz.auth.query;

import lombok.Data;

@Data
public class QuestionQuery extends BaseQuery{
    /**
     * 问题的名称
     */
   private String questionTitle;

}