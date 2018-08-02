package com.ptteng.academy.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: canary
 * @description: 账号业务层对象
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 01:06
 **/
@ApiModel(value = "AccountDto",       // 模型名称
        description = "账号Dto")      // 描述
@Data
public class AccountDto implements Serializable{
    private static final long serialVersionUID = -4839818108799795556L;

    @ApiModelProperty(notes = "id", required = false)
    private Long id;
    // 账号名
    @ApiModelProperty(notes = "账号名", required = false)
    private String username;
    @ApiModelProperty(notes = "密码", required = false)
    private String password;
    @ApiModelProperty(notes = "老密码", required = false)
    private String oldPassword;
    // 角色名
    @ApiModelProperty(notes = "角色名", required = false)
    private String role_name;
    // 角色名
    @ApiModelProperty(notes = "角色id", required = false)
    private Long role_id;
    @ApiModelProperty(notes = "创建时间", required = false)
    private Date create_at;
    @ApiModelProperty(notes = "创建人", required = false)
    private String create_by;
}
