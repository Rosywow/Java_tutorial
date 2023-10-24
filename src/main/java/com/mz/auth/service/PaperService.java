package com.mz.auth.service;


import com.mz.auth.entity.Paper;
import com.mz.auth.entity.PaperQuestion;
import com.mz.auth.query.PaperQuery;
import com.mz.auth.util.PageList;
import com.mz.auth.vo.PaperGengerateVO;
import com.mz.auth.vo.TypeTotalVO;

import java.util.List;
import java.util.Map;

/**
 * @description:
 */

public interface PaperService {


    //分页查询数据
    PageList listPage(PaperQuery paperQuery);
    //保存试卷
    void savePaper(Paper paper);

    void deletePaper(Long id);
    //查询所有试卷
    List<Paper> queryPaper();
    //手动组卷
    void diyPaperQuestion(PaperQuestion paperQuestion);
    //根据试卷id查询所有的问题
    List<PaperQuestion> queryQuestionByPaperId(Long paperId);

    //查询题类型的总数
    List<TypeTotalVO> queryTypeTotal();
    //根据id查询预览试卷信息
    PaperGengerateVO previewPaper(Long paperId);

    void randomPaperQuestion(Map mp);

    List<Paper> queryAll();
/*


    //修改保存试卷
    void editSavePaper(Paper paper);

    //删除试卷
    void deletePaper(Long id);

    //试题组卷：查询所有的试卷
    List<Paper> queryPaper();

    //试题组卷：根据试卷id查询对应的问题记录
    List<PaperQuestion> queryQuestionByPaperId(Long paperId);

    //手动组卷
    void diyPaperQuestion(PaperQuestion paperQuestion);


    //预览试卷方法
    PaperGengerateVO previewPaper(Long paperId);

    //查询题类型的总数
    List<TypeTotalVO> queryTypeTotal();

    //随机组卷
    void randomPaperQuestion(Map mp);

    //查询所有的试卷
    List<Paper> queryAll();*/
}









