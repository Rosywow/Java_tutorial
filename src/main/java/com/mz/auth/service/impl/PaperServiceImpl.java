package com.mz.auth.service.impl;


import com.mz.auth.entity.Paper;
import com.mz.auth.entity.PaperQuestion;
import com.mz.auth.mapper.PaperMapper;
import com.mz.auth.mapper.QuestionMapper;
import com.mz.auth.query.PaperQuery;
import com.mz.auth.service.PaperService;
import com.mz.auth.util.PageList;
import com.mz.auth.vo.PaperGengerateVO;
import com.mz.auth.vo.TypeTotalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @description:
 */
@Service
//声明事务   方法上有声明事务的注解时，以Propagation.SUPPORTS就以事务的方式运行
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;


    @Autowired
    private QuestionMapper questionMapper;

   /* @Autowired
    private QuestionMapper questionMapper;*/

    //分页查询数据
    @Override
    public PageList listPage(PaperQuery paperQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = paperMapper.queryTotal(paperQuery);
        pageList.setTotal(total);
        //查询每页的数据
        List<Paper> papers =  paperMapper.queryData(paperQuery);
        pageList.setRows(papers);
        return pageList;
    }

    //保存试卷
    @Override
    public void savePaper(Paper paper) {
        paper.setStatus(0);//默认表示试卷状态为 已创建   0为正在使用，1为已停用
        paper.setCreateTime(new Date());//试卷的创建时间
        paperMapper.savePaper(paper);
    }





    /**
     * 删除试卷
     *     1.删除试卷对应的组记录 exam_paper_question paperid=10017
     *     2.删除exam_paper的记录  paperid = 10017
     */
    @Override
    @Transactional
    public void deletePaper(Long id) {
        //删除试卷的组题记录
        paperMapper.deletePaperQuestion(id);
        //删除试卷
        paperMapper.deletePaper(id);
    }

    //试卷组卷：查询所有的试卷
    @Override
    public List<Paper> queryPaper() {
        return paperMapper.queryPaper();
    }


    /** 手动组卷 逻辑
     *   （1）先删除试卷对应的问题（因为试卷名称不变，组成新的试卷，就要把原来的试卷的题目删掉，才能组成新的试卷）
     *   （2）再把问题 批量插入试卷对应的问题表 （因为一张试卷对应多个问题，所以要完成批量插入）
     */
    @Override
    @Transactional
    public void diyPaperQuestion(PaperQuestion paperQuestion) {
        //（1）先删除试卷对应的问题
        paperMapper.deletePaperQuestion(paperQuestion.getPaperId());
        //（2）再把问题 插入试卷对应的问题表 完成批量插入 [{paperId:1111,questionId:123},{paperId:1111,questionId:124}]
        //一个paperId对应多个questionId 即一张试卷对应多个问题   //stream流循环取出每一个id（即问题id）
        List<Map> params = paperQuestion.getQuestionIdsList().stream().map(question -> {//取出question实体表对象 的每一项即每一条问题的id
            Map mp = new HashMap();
            mp.put("paperId", paperQuestion.getPaperId());
            mp.put("questionId", question.getId());
            return mp;
        }).collect(Collectors.toList());
        paperMapper.insertBatchPaperQuestion(params);//批量插入
    }

    //试卷组卷： 根据试卷id 查询试卷对应的问题
    @Override
    public List<PaperQuestion> queryQuestionByPaperId(Long paperId) {
        return paperMapper.queryQuestionByPaperId(paperId);
    }


    //查询题型的总数
    @Override
    public List<TypeTotalVO> queryTypeTotal() {
        return paperMapper.queryTypeTotal();
    }

    //预览试卷方法
    @Override
    public PaperGengerateVO previewPaper(Long paperId) {
        return paperMapper.previewPaper(paperId);
    }



    @Override
    @Transactional
    public void randomPaperQuestion(Map mp) {
        //创建随机生成所有题目类型 id集合
        List ids = new ArrayList();

        //（1）获取前台传过来的5个参数    mp.get("paperId")默认是string类型，需要转long类型 Long.valueOf（ ）
        Long paperId =   Long.valueOf((String)mp.get("paperId"));
        Long xztNum =   Long.valueOf((String)mp.get("xztNum"));
        Long tktNum =   Long.valueOf((String)mp.get("tktNum"));
        Long pdtNum =   Long.valueOf((String)mp.get("pdtNum"));
        Long jdtNum =   Long.valueOf((String)mp.get("jdtNum"));
        //（2）查询出所有的选择题的id   根据选择题类型 q_typeid为1
        List xztIds = questionMapper.queryQuestionIdByTypeId(1L);
        //从查询出结果里面 随机选择id
        for (int i = 0; i < xztNum; i++) {//循环遍历取出数组
            Object target =  xztIds.get(new Random().nextInt(xztIds.size())); //根据该类型id集合长度获取随机题目id
            ids.add(target);//取出id放入集合
            xztIds.remove(target);//为避免选择重复需要删除已经选中过的ID
        }
        //（3)填空题
        List tktIds = questionMapper.queryQuestionIdByTypeId(2L);//查询出所有的填空题的id
        for (int i = 0; i < tktNum; i++) {
            //该值介于[0,10)的区间
            Object target =  tktIds.get(new Random().nextInt(tktIds.size())); //888
            ids.add(target);
            tktIds.remove(target);
        }
        //(3)判断题
        List pdtIds = questionMapper.queryQuestionIdByTypeId(3L);
        for (int i = 0; i < pdtNum; i++) {
            //该值介于[0,10)的区间
            Object target =  pdtIds.get(new Random().nextInt(pdtIds.size())); //888
            ids.add(target);
            pdtIds.remove(target);
        }
        //(3)简答题
        List jdtIds = questionMapper.queryQuestionIdByTypeId(4L);
        for (int i = 0; i < jdtNum; i++) {
            //该值介于[0,10)的区间
            Object target =  jdtIds.get(new Random().nextInt(jdtIds.size())); //888
            ids.add(target);
            jdtIds.remove(target);
        }
        //（4）先删除试卷对应的问题
        paperMapper.deletePaperQuestion(paperId);
        //再插入试卷对应的问题表 完成批量插入   一张试卷对应多条问题[{paperId:1111,questionId:123},{paperId:1111,questionId:124}]
        List<Map> params = (List)ids.stream().map(id -> {//stream流循环取出每一个id（即问题id）
            Map mp1 = new HashMap();
            mp1.put("paperId", paperId);
            mp1.put("questionId", id);//questionId即实体表question的id属性
            return mp1;
        }).collect(Collectors.toList());
        // 调用批量插入方法
        paperMapper.insertBatchPaperQuestion(params);

    }

    //查询所有有效的试卷
    @Override
    public List<Paper> queryAll() {
        return paperMapper.queryAll();
    }

}
