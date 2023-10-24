package com.mz.auth.mapper;

import com.mz.auth.entity.Paper;
import com.mz.auth.entity.PaperQuestion;
import com.mz.auth.query.PaperQuery;
import com.mz.auth.vo.PaperGengerateVO;
import com.mz.auth.vo.TypeTotalVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author cr
 * @date 2023/2/16
 * @description
 */
@Mapper
public interface PaperMapper {

    //查询总的条数
    Long queryTotal(PaperQuery paperQuery);

    //查询当前页的数据
    List<Paper> queryData(PaperQuery paperQuery);
    //新增试卷
    @Insert("insert into exam_paper(name,startTime,endTime,createTime,status,levelid) " +
            "values (#{name},#{startTime},#{endTime},#{createTime},#{status},#{levelid})")
    void savePaper(Paper paper);

    //删除组题记录 paperId=#{id}
    @Delete("delete from exam_paper_question where paperId=#{id}")
    void deletePaperQuestion(Long id);

    //删除试卷 id=#{id}
    @Delete("delete from exam_paper where id=#{id}")
    void deletePaper(Long id);

    //试题组卷：查询所有的试卷
    @Select("select * from exam_paper")
    List<Paper> queryPaper();

    /**
     * 批量插入试卷对应的 试题表 里面 即试卷问题表
     * @param params
     */
    @Insert("<script>insert into exam_paper_question(paperId,questionId) " +
            "values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.paperId},#{item.questionId})"+
            "</foreach>"+
            "</script>")
    void insertBatchPaperQuestion(List<Map> params);

    @Select("select * from exam_paper_question where paperId=#{paperId}")
    List<PaperQuestion> queryQuestionByPaperId(Long paperId);


    //查询题型的总数  group by根据 q_typeid来查询totalNum总数量
    @Select("select q_typeid,count(*) totalNum\n" +
            "from exam_questionbank\n" +
            "group by q_typeid")
    List<TypeTotalVO> queryTypeTotal();

    PaperGengerateVO previewPaper(Long paperId);

    //查询所有的试卷  过滤掉已停用的试卷  status = 0    1为停用
    @Select("select * from exam_paper where status = 0")
    List<Paper> queryAll();
}

