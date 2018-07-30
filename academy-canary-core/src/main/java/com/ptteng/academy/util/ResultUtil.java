package com.ptteng.academy.util;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.consts.CommonConst;
import com.ptteng.academy.business.dto.StudyDto;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;

import java.util.List;
import java.util.Map;

/**
 * @program: canary
 * @description: 接口返回工具类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 02:04
 **/

public class ResultUtil {

    public static ResponseVO error(int code, String message) {
        return vo(code, message);
    }

    public static ResponseVO error(ResponseCodeEnum status) {
        return vo(status.getCode(), status.getMessage());
    }

    public static ResponseVO error(String message) {
        return vo(CommonConst.DEFAULT_ERROR_CODE, message);
    }

    public static ResponseVO success(String message) {
        return vo(CommonConst.DEFAULT_SUCCESS_CODE, message);
    }

    public static ResponseVO success(ResponseCodeEnum code) {
        return vo(code.getCode(), code.getMessage());
    }
    public static ResponseVO success(String message, Object data) {
        return vo(CommonConst.DEFAULT_SUCCESS_CODE, message, data);
    }

    public static ResponseRowsVO success(String message, PageInfo<?> pageInfo) {
        return vo(CommonConst.DEFAULT_SUCCESS_CODE, message, pageInfo);
    }

    public static ResponseRowsVO success(String message, List<?> data) {
        return vo(CommonConst.DEFAULT_SUCCESS_CODE, message, 123L, data);
    }
    public static ResponseRowsVO success(String message, Long total, List<?> data) {
        return vo(CommonConst.DEFAULT_SUCCESS_CODE, message, total, data);
    }

    /* 调用外部接口 */
    // 自定以状态码
    private static ResponseVO vo(int code, String message) {
        return new ResponseVO<>(code, message, null);
    }
    // 单条数据
    private static ResponseVO vo(int code, String message, Object data) {
        return new ResponseVO<>(code, message, data);
    }
    // 多条数据
    private static ResponseRowsVO vo(int code, String message, Long total, List<?> data) {
        return new ResponseRowsVO<>(code, message, total, data.size(), data);
    }
    // 分页
    private static ResponseRowsVO vo(int code, String message, PageInfo<?> pageInfo) {
        return new ResponseRowsVO<>(code, message, pageInfo.getTotal(), pageInfo.getSize(), pageInfo.getList());
    }
    private static ResponseRowsVO vo2(Integer code, String message) {
        return new ResponseRowsVO(code, message);
    }
}
