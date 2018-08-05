package com.ptteng.academy.business.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: canary
 * @description: 用户查询条件
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 03:08
 **/

@ApiModel(value = "UserQuery",       // 模型名称
        description = "微信用户查询条件",      // 描述
        parent = IQuery.class)    // 父类
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQuery extends IQuery{
    @ApiModelProperty(notes = "用户id", required = false)
    private Long id;    // id
    @ApiModelProperty(notes = "昵称", required = false)
    private String nickName;    // 昵称
    @ApiModelProperty(notes = "email", required = false)
    private String email;
    @ApiModelProperty(notes = "手机号", required = false)
    private String phone;
    @ApiModelProperty(notes = "逆袭豆范围", required = false, dataType = "Integer[]")
    private Integer[] bean; // 一个数组, 包含一个范围
    @ApiModelProperty(notes = "所在地区", required = false)
    private String prefecture;  // 所在地区
    @ApiModelProperty(notes = "年级, 1-6对应不同年级, 0代表查询全部", required = false)
    private Integer grade;
    @ApiModelProperty(notes = "冻结状态", required = false)
    private Boolean status;
    @ApiModelProperty(notes = "需要显示的页数（仅用户管理需要）", required = false)
    private Integer pageNum;
    @ApiModelProperty(notes = "每一页显示的条数（仅用户管理需要）", required = false)
    private Integer pageSize;
}
