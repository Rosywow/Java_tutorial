package com.mz.auth.query;

import lombok.Data;

@Data
public class ScoreDetailQuery  extends BaseQuery{
    //试题的编号
    private Long questionId;
    //问题编号
    private Long paperId;
}