package com.mz.auth.service;

import com.mz.auth.entity.Question;
import com.mz.auth.query.QuestionQuery;
import com.mz.auth.util.PageList;

/**
 * @author cr
 * @date 2023/2/17
 * @description
 */
public interface QuestionService {
    //分页查询
    PageList listPage(QuestionQuery questionQuery);
    //新增题目方法
    void addQuestion(Question question);
    //根据题目id查询题目内容
    Question queryQuestionByQid(Long qid);

    void editQuestion(Question question);

    //删除问题
    void deleteQuestion(Long id);
}
