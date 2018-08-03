package com.ptteng.academy.business.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description:返回的页数信息
 * author:Lin
 * Date:2018/7/21
 * Time:10:39
 */
@Data
public class PageQuery {
    private Integer pageNum;
    private Integer pageSize;
    private Long userId;
}
