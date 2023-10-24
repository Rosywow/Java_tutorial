package com.mz.auth.service.impl;

import com.mz.auth.entity.ScoreDetail;
import com.mz.auth.mapper.ScoreDetailMapper;
import com.mz.auth.query.ScoreDetailQuery;
import com.mz.auth.service.ScoreDetailService;
import com.mz.auth.util.PageList;
import com.mz.auth.vo.StuPaperQuestionVo;
import com.mz.auth.vo.StuScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreDetailServiceImpl implements ScoreDetailService {

    @Autowired
    private ScoreDetailMapper scoreDetailMapper;

    //保存考试记录
    @Override
    public void savePaperTestRecord(List<ScoreDetail> scoreDetailList) {
        scoreDetailMapper.savePaperTestRecord(scoreDetailList);//调用
    }

    //老师阅卷：分页查询考试记录
    @Override
    public PageList listPage(ScoreDetailQuery scoreDetailQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = scoreDetailMapper.queryTotal(scoreDetailQuery);
        pageList.setTotal(total);
        //查询每页的数据
        List<ScoreDetail> scoreDetails =  scoreDetailMapper.queryData(scoreDetailQuery);
        pageList.setRows(scoreDetails);
        return pageList;
    }

    @Override
    public void updateJdtScore(ScoreDetail scoreDetail) {
        scoreDetailMapper.updateJdtScore(scoreDetail);
    }
    @Override
    public List<StuScoreVO> queryFrontAllStuScore(Long stuId) {
        return scoreDetailMapper.queryFrontAllStuScore(stuId);
    }

    //查询学生考卷的每个问题细节情况
    @Override
    public StuPaperQuestionVo queryPaperDetail(ScoreDetail scoreDetail) {
        return scoreDetailMapper.queryPaperDetail(scoreDetail);
    }
}
