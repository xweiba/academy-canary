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
public class ResponseVO<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseVO(ResponseCodeEnum code, T data) {
        // 通过状态码获取状态信息后调用ResponseVO(Integer code, String message, T data) 构造方法
        this(code.getCode(), code.getMessage(), data);
    }
}
