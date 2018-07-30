package com.ptteng.academy.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ptteng.academy.persistence.beans.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: canary
 * @description: 作者Dto
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 16:14
 **/
@ApiModel(value = "AuthorDto",       // 模型名称
        description = "作者Dto")      // 描述
@Data
public class AuthorDto {
    @ApiModelProperty(notes = "id", required = false)
    private Long id;
    @ApiModelProperty(notes = "名称", required = false)
    private String author_name;
    @ApiModelProperty(notes = "头像地址", required = false)
    private String author_img;
}
