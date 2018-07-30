package com.ptteng.academy.business.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @program: canary
 * @description: 分类Dto
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 16:07
 **/

@Data
public class ClassifyDto {
    // 分类名称, 根据类型查找分类的所有值
    private Integer id;
    private String name;
}
