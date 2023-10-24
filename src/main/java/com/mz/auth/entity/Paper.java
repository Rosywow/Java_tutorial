package com.mz.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Paper {
  
    private Long id;//试卷主键id
    
    private String name;//试卷名称
 
    private Integer status;//试卷状态
   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //(返回的时间类型)前台页面展示的时间格式
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss") //( 前台传来 )后台接收时间的类型
    private Date startTime;//测试开始时间
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //(返回的时间类型)前台页面展示的时间格式
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss")//( 前台传来 )后台接收时间的类型
    private Date endTime;//测试结束时间
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//(返回的时间类型)前台页面展示的时间格式
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss")//( 前台传来 )后台接收时间的类型
    private Date createTime;//时间创建时间
    
    private Long levelid;//试卷等级
    
    private String levelname;//试卷等级名称

}