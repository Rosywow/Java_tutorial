package com.mz.auth.entity;

import lombok.Data;

@Data
public class QuestionType {
   
    private Long id;//主键id
    
    private String name;//题的类型的名称
   
    private String desc;//类型的描述
   
    private String typenum;//类型的编号

}