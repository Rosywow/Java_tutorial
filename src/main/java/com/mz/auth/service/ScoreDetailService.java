package com.mz.auth.service;

import com.mz.auth.entity.ScoreDetail;
import com.mz.auth.query.ScoreDetailQuery;
import com.mz.auth.util.PageList;
import com.mz.auth.vo.StuPaperQuestionVo;
import com.mz.auth.vo.StuScoreVO;

import java.util.List;

public interface ScoreDetailService {
    //保存考试记录
    void savePaperTestRecord(List<ScoreDetail> scoreDetailList);

    //老师阅卷：分页查询考试记录数据
    PageList listPage(ScoreDetailQuery scoreDetailQuery);

    void updateJdtScore(ScoreDetail scoreDetail);

    //查询
    List<StuScoreVO> queryFrontAllStuScore(Long stuId);


    //查询学生考卷的每个问题的细节
    StuPaperQuestionVo queryPaperDetail(ScoreDetail scoreDetail);
}