package com.mz.auth.mapper;

import com.mz.auth.entity.ScoreDetail;
import com.mz.auth.query.ScoreDetailQuery;
import com.mz.auth.vo.StuPaperQuestionVo;
import com.mz.auth.vo.StuScoreVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//考试记录
@Mapper
public interface ScoreDetailMapper {
    //批量插入  exam_scoredetail考试记录表
    @Insert("<script>insert into exam_scoredetail(stuId,paperId,questionId," +
            "questionTitle,q_typeid," +
            "questionAnswer,questionScore,correntAnswer,correntScore) values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.stuId},#{item.paperId},#{item.questionId},#{item.questionTitle}," +
            "#{item.q_typeid},#{item.questionAnswer},#{item.questionScore}," +
            "#{item.correntAnswer},#{item.correntScore})" +
            "</foreach>" +
            "</script>")
    void savePaperTestRecord(List<ScoreDetail> scoreDetailList);


    //老师阅卷：查询考试记录的总数
    Long queryTotal(ScoreDetailQuery scoreDetailQuery);

    //老师阅卷：查询考试记录的分页数据
    List<ScoreDetail> queryData(ScoreDetailQuery scoreDetailQuery);

    //老师阅卷操作 根据id设置exam_scoredetail表字段 correntscore
    @Update("update exam_scoredetail set correntscore=#{correntScore} where id=#{id}")
    void updateJdtScore(ScoreDetail scoreDetail);

    //查询学生全科目成绩
    List<StuScoreVO> queryFrontAllStuScore(@Param("stuId") Long stuId);

    StuPaperQuestionVo queryPaperDetail(ScoreDetail scoreDetail);
}