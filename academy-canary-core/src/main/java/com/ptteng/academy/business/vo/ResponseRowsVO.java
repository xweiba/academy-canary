package com.ptteng.academy.business.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.List;

/**
 * @program: canary
 * @description: Controller 返回数据格式
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 02:30
 **/

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseRowsVO<T> {
    private Integer code;
    private String message;
    private Long total;
    private Integer rows;
    private List<T> data;

    public ResponseRowsVO(Integer code, String message, Long total, Integer rows, List<T> data) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.rows = rows;
        this.data = data;
    }

    public ResponseRowsVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
