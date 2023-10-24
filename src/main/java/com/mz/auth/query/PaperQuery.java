package com.mz.auth.query;

import lombok.Data;

/**
 * @description: 
 * UserQuery 用来接收前台传递参数name 
 * 按照试卷名称进行查询
 */
@Data
public class PaperQuery extends BaseQuery{//extends BaseQuery

    private Long id;
    /**
     * 试卷名称
     */
    private String name;

}
