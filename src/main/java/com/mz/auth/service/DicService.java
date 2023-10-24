package com.mz.auth.service;

import com.mz.auth.entity.DicTypeData;

import java.util.List;

public interface DicService {
    //查询试卷等级
    List<DicTypeData> findLevels();
}

