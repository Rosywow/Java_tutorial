package com.mz.auth.entity;

import lombok.Data;

@Data
public class DicTypeData {
    private Long id;//主键信息
    private String name;//数据项对应数据
    private Long typeid;//关联类型表id
}