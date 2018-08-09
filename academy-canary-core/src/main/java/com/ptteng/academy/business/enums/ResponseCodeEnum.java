package com.ptteng.academy.business.enums;

/**
 * @program: canary
 * @description: 枚举返回状态码
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-18 02:32
 **/

public enum ResponseCodeEnum {
    SUCCESS(200, "操作成功！"),
    FIND_NULL_SUCCESS(200,"未查询到数据"),
    ERROR(500, "服务器未知错误！"),
    NULL_POINTER_ERROR(500,"资源属性未正常初始化"),
    UNAUTHORIZED(500, "尚未登录！"),
    FORBIDDEN(500, "您没有该接口的操作权限！"),
    LOGIN_ERROR(500, "账号或密码错误！"),
    USER_EXIST(500, "已存在的用户！"),
    INVALID_AUTHCODE(500, "手机验证码无效！"),
    INVALID_TOKEN(500, "无效的TOKEN，您没有操作权限！"),
    INVALID_ACCESS(500, "无效的请求，该请求已过期！"),
    DELETE_ERROR(500, "删除失败！"),
    USER_UNEXIST(501,"用户或请求资源不存在！"),
    BEAN_UTIL_ERROR(500,"数据转换失败! 请检查传入参数!"),
    CAST_TO_ERROR(500, "类型转换失败!"),
    IO_EXCEPTION_ERROR(500,"文件路径设置失败"),
    FILE_NOT_FOUND_ERROR(500,"File转换InputStream失败"),
    SQL_PARAMETER_ERROR(500, "属性值不能为空"),
    SQL_KEY_PARAMETER_ERROR(500, "提交的属性值不能与已有属性值重复"),
    SQL_RESOURCE_ERROR(500,"资源不存在"),
    SQL_PARAM_ERROR(500,"查询参数错误"),
    REDIS_GET_ERROR(500,"Redis初始化失败"),
    SMS_SEND_ERROR(500,"短信发送失败");

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
