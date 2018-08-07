package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: canary
 * @description: 角色
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 09:17
 **/

@Slf4j
@Api(tags = "RoleController", description = "角色相关Api")
@RestController
public class RoleController {

    @Resource
    private ManageService manageService;

    /**
     * @description 获取角色列表
     * @param: []
     */

    @RequiresPermissions(".role")
    @ApiOperation(value = "获取角色列表", notes = "所有角色")
    @GetMapping("/rolenames")
    public ResponseRowsVO roleNames() throws Exception {
        return ResultUtil.success("roleNames 已执行", manageService.findRoleNames());
    }

    @RequiresPermissions(".role")
    @ApiOperation(value = "分页查询角色信息", notes = "所有角色")
    @PostMapping("/roles")
    public ResponseRowsVO getRoles(@RequestBody RoleQuery roleQuery) throws Exception {
        log.info("roleQuery.toString():" + roleQuery.toString());
        return ResultUtil.success("获取角色成功", manageService.findRoleByQuery(roleQuery));
    }

    @RequiresPermissions(".role")
    @ApiOperation(value = "通过id获取角色详细信息", notes = "所有角色")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/role/{id}")
    public ResponseVO getRole(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("获取角色详细信息成功", manageService.findRoleById(id));
    }

    @RequiresPermissions(".role")
    @ApiOperation(value = "创建角色")
    @PostMapping("/role")
    public ResponseVO createRole(@RequestBody RoleDto roleDto) throws Exception {
        return ResultUtil.success("角色创建成功", manageService.insertRole(roleDto));

    }

    @RequiresPermissions(".role")
    @ApiOperation(value = "更新角色")
    @PutMapping("/role")
    public ResponseVO updateRole(@RequestBody RoleDto roleDto) throws Exception {
        return ResultUtil.success("角色更新成功", manageService.updateRole(roleDto));
    }

    @RequiresPermissions(".role")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    public ResponseVO deleteRole(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("删除角色成功", manageService.deleteRoleById(id));
    }
}