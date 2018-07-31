package com.ptteng.academy.persistence.mapper;

import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.persistence.beans.Role;
import com.ptteng.academy.plugin.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: canary
 * @description: 角色
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-31 17:25
 **/

public interface RoleMapper extends BaseMapper<Role> {
    List<RoleDto> findRoleByQuery(RoleQuery roleQuery);

    @Select("SELECT id, role_name FROM role")
    List<RoleDto> findRoleNames();
}
