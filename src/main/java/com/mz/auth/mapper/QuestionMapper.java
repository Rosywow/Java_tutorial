package com.mz.auth.mapper;

import com.mz.auth.entity.Question;
import com.mz.auth.entity.QuestionXztOptions;
import com.mz.auth.query.QuestionQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author cr
 * @date 2023/2/17
 * @description
 */
@Mapper
public interface QuestionMapper {
    //查询问题的总数
    Long queryTotal(QuestionQuery questionQuery);
    //分页查询的数据
    List<Question> queryData(QuestionQuery questionQuery);

    //保存题目的方法
    @Insert("insert into exam_questionbank(questionTitle,questionAnswer,grade,q_typeid,status,createTime,creatorId)" +
            "values(#{questionTitle},#{questionAnswer},#{grade},#{q_typeid},#{status},#{createTime},#{creatorId})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void addQuestion(Question question);

    //保存选择题的选项方法
    @Insert("insert into exam_xzt_options(optionA,optionB,optionC,optionD,questionId)" +
            " values(#{optionA},#{optionB},#{optionC},#{optionD},#{questionId})")
    void addXztOptions(QuestionXztOptions questionXztOptions);



    //1.先查题库表 id=#{qid})根据问题的id查询问题
    @Select("select  * from exam_questionbank where id=#{qid}")
    @Results(value={
            @Result(column = "id",property = "id",id=true),
            //Question实体表对象里面有  questionXztOptions对象（exam_xzt_options数据库表），所以要去映射 exam_xzt_options表，再去查选择题选项表
            @Result(column = "id",property = "questionXztOptions",
                    //getQuestionOptionsByQid为方法名，通过该方法去查exam_xzt_options表
                    one=@One(select="getQuestionOptionsByQid"))
    })
    Question queryQuestionByQid(Long qid);

    //2.再查选择题选项表
    @Select("select * from exam_xzt_options where questionId=#{id}")
    //查的是exam_xzt_options选择题选项表数据库表  返回的自然也是选项表对象QuestionXztOptions
    QuestionXztOptions getQuestionOptionsByQid(Long qid);



    //题库表
    // 根据问题（题目）id  更新数据  id=#{id}  question实体表对象有前台传过来的的参数id
    @Update("update exam_questionbank set questionTitle=#{questionTitle}" +
            ",questionAnswer=#{questionAnswer},grade=#{grade}," +
            "q_typeid=#{q_typeid},creatorId=#{creatorId} where id=#{id} ")
    void updateQuestion(Question question);

    //项表
    //根据问题id 删除选择题选项表数据   questionId=#{id}
    @Delete("delete from exam_xzt_options where questionId=#{id}")
    void deleteXztOptions(Long id);


    //根据问题id 删除问题数据
    @Delete("delete from exam_questionbank where id=#{id}")
    void deleteQuestion(Long id);

    //查询所有的对应题型的 问题id 都有哪些
    @Select("select id from exam_questionbank where q_typeid=#{q_typeid} ")
    @ResultType(Long.class)
    List<Long> queryQuestionIdByTypeId(long q_typeid);
}
