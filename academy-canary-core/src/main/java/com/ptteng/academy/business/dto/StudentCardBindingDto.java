package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:学生绑定信息的状态
 * author:Lin
 * Date:2018/7/20
 * Time:16:16
 */
@Data
public class StudentCardBindingDto {
    private long phone;
    private String email;
}
