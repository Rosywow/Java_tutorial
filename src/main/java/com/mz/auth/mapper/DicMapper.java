package com.mz.auth.mapper;

import com.mz.auth.entity.DicTypeData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: DicMapper
 */
@Mapper
public interface DicMapper {

    //查询试卷等级
    @Select("select * from t_dictype_data where typeid=1")
    List<DicTypeData> findLevels();
}
