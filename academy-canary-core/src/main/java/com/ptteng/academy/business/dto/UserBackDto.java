package com.ptteng.academy.business.dto;

import lombok.Data;

/**
 * description:
 * author:Lin
 * Date:2018/8/2
 * Time:21:29
 */
@Data
public class UserBackDto {
    private Long id;
    private String nick_name;
    private String email;
    private Long phone;
    private String grade;
    private String prefecture;
    private Integer bean;
    private Boolean status;
}
