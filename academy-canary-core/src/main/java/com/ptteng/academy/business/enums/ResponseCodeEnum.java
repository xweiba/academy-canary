package com.ptteng.academy.business.enums;

/**
 * @program: canary
 * @description: 枚举返回状态码
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 02:32
 **/

public enum ResponseCodeEnum {
    SUCCESS(200, "操作成功！"),
    ERROR(500, "服务器未知错误！"),
    UNAUTHORIZED(500, "尚未登录！"),
    FORBIDDEN(500, "您没有操作权限！"),
    LOGIN_ERROR(500, "账号或密码错误！"),
    USER_EXIST(500, "已存在的用户！"),
    INVALID_AUTHCODE(500, "手机验证码无效！"),
    INVALID_TOKEN(500, "无效的TOKEN，您没有操作权限！"),
    INVALID_ACCESS(500, "无效的请求，该请求已过期！"),
    DELETE_ERROR(500, "删除失败！"),
    USER_UNEXIST(501,"用户或请求资源不存在！");

    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCodeEnum getResponseStatus(String message) {
        for (ResponseCodeEnum ut : ResponseCodeEnum.values()) {
            if (ut.getMessage() == message) {
                return ut;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
