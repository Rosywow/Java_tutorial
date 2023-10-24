package com.mz.auth.service.impl;

import com.mz.auth.entity.Question;
import com.mz.auth.entity.QuestionXztOptions;
import com.mz.auth.mapper.QuestionMapper;
import com.mz.auth.query.QuestionQuery;
import com.mz.auth.service.QuestionService;
import com.mz.auth.util.CommonUtils;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    //分页查询数据
    @Override
    public PageList listPage(QuestionQuery questionQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        Long total = questionMapper.queryTotal(questionQuery);
        pageList.setTotal(total);
        //查询每页的数据
        List<Question> questions = questionMapper.queryData(questionQuery);
        pageList.setRows(questions);
        return pageList;
    }

    @Override
    @Transactional  //涉及两张表，添加事务
    public void addQuestion(Question question) {
        //第一张表exam_questionbank
        //（1）把题目 保存在 题库表exam_questionbank
        question.setStatus(0L);
        question.setCreateTime(new Date());
        //返回主键id，用于把 选项ABCD 保存进 表exam_xzt_options
        question.setCreatorId(CommonUtils.getLoginUser().getId());
        //此时question对象不仅有前台传来的参数，还有设置的字段 Status状态  CreateTime创建时间   CreatorId创建者
        questionMapper.addQuestion(question);

        //第二张表exam_xzt_options 选择题选项表 abcd
        //如果是选择题 则把选择题的 选项 保存进 选项表
        if(question.getQ_typeid()==1L){//（1.题型 选择题）如果是选择题
            QuestionXztOptions questionXztOptions = question.getQuestionXztOptions();//question.getQuestionXztOptions()（2.选项 ABCD）
            questionXztOptions.setQuestionId(question.getId()); //即questionId=id 把两张表联系起来
            //此时的questionXztOptions包括了questionId
            questionMapper.addXztOptions(questionXztOptions);
        }
    }

    //据问题id查询问题   返回值类型为Question实体表
    @Override
    public Question queryQuestionByQid(Long qid) {
        return questionMapper.queryQuestionByQid(qid);
    }


     //根据id修改问题方法
     //不管什么题型 都去删除一下 选择题选项数据  如果传过来的是选择题，先删除再插入
    @Override
    @Transactional
    public void editQuestion(Question question) {
        question.setCreatorId(CommonUtils.getLoginUser().getId());//重新获取登录用户的id(即题目创建者)，防止其他人登录把题目改了，所以重新获取
        //修改问题
        questionMapper.updateQuestion(question);
        //如果是选择题
        questionMapper.deleteXztOptions(question.getId());
        if(question.getQ_typeid()==1L){
            questionMapper.addXztOptions(question.getQuestionXztOptions());
        }
    }

    /**
     * 根据问题id删除问题
     *第一种方法： 根据id查询的问题  根据typeid 决定删不删除选项表数据   在删除问题表
     * 第二种： 不管37 21  先删除选项表（没有则说明选中的题目不是选择题，那这步代码就无效，不管） 再删除问题表（问题类型）  （方法简单）
     */
    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        //先删除选择题的选项表（没有则不用管，执行下一步删除问题表，先执行这步是为了先过滤掉选择题题型）
        questionMapper.deleteXztOptions(id);
        //在删除问题表
        questionMapper.deleteQuestion(id);
    }

}