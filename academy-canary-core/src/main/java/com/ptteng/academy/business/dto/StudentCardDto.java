package com.ptteng.academy.business.dto;

import lombok.Data;

/**
 * description:学生证返回信息
 * author:Lin
 * Date:2018/7/20
 * Time:11:11
 */
@Data
public class StudentCardDto {
    private Long id;
    private String nickName;
    private String headImgUrl;
    private Integer grade;
    private long bean;
    private boolean bingding;
    private String media_id;
}
