package com.ptteng.academy.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:学生绑定信息时返回 的
 * author:Lin
 * Date:2018/7/20
 * Time:18:50
 */
@Data
public class StudentBindStatusDto {
    private boolean state;
    private long gainBean;
}
