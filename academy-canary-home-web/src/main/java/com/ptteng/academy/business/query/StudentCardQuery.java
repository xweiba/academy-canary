package com.ptteng.academy.business.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * description:
 * author:Lin
 * Date:2018/7/24
 * Time:17:00
 */
@ApiModel
@Data
public class StudentCardQuery {
    String nickName;
    String grade;
}
