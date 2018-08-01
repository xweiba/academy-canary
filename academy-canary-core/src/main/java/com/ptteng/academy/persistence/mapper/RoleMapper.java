package com.ptteng.academy.persistence.mapper;

import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.persistence.beans.Role;
import com.ptteng.academy.plugin.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: canary
 * @description: 角色
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-31 17:25
 **/

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<RoleDto> findRoleByQuery(RoleQuery roleQuery);

    @Select("SELECT id, role_name FROM role")
    List<RoleDto> findRoleNames();

    @Select("SELECT romd.modules_id FROM role ro INNER JOIN role_modules romd ON ro.id = romd.role_id WHERE ro.id = #{id}")
    List<Long> findRoleModuleById(Long id);

    @Select("SELECT ro.id, ro.role_name, ro.create_at, ro.create_by FROM role ro WHERE id = #{id}")
    RoleDto findRoleById(Long id);

    // 删除角色关系表
    @Select("DELETE FROM role_modules WHERE role_id = #{id}")
    Boolean deleteRoleModulesByRoleId(Long id);

    // 添加角色关系表
    @Select("INSERT INTO role_modules(role_id, modules_id) VALUES(#{roleId},#{moduleId});")
    Boolean insertRoleModulesByRoleId(@Param("roleId") Long roleId, @Param("moduleId") Long moduleId);
}
