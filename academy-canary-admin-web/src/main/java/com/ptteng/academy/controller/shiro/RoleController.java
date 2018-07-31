package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    @ApiOperation(value = "获取角色列表", notes = "所有角色")
    @GetMapping("/rolenames")
    public ResponseRowsVO roleNames() {
        return ResultUtil.success("roleNames 已执行", manageService.findRoleNames());
    }

    @PostMapping("/roles")
    public ResponseRowsVO getRoles(@RequestBody RoleQuery roleQuery) {
        log.info("roleQuery.toString():" + roleQuery.toString());
        return ResultUtil.success("getRoles 已执行", manageService.findRoleByQuery(roleQuery));
    }

    @GetMapping("/role/{id}")
    public ResponseVO getRole(@PathVariable("id") Long id) {
        RoleDto roleDto = new RoleDto();
        Integer [] nums = {0,1,2,3,4,5,6,7,8,9};
        roleDto.setId(id);
        roleDto.setRole_name("葫芦娃" + id);
        roleDto.setCreate_at(new Date());
        roleDto.setCreate_by("admin" + id);
        roleDto.setModuleIds(nums);
        return ResultUtil.success("getRole_name 已执行", roleDto);
    }
    @PostMapping("/role")
    public ResponseVO createRole(@RequestBody RoleDto roleDto) {
        return ResultUtil.success("createRole 已执行", roleDto);
    }

    @PutMapping("/role")
    public ResponseVO updateRole(@RequestBody RoleDto roleDto) {
        return ResultUtil.success("updateRole 已执行", roleDto);
    }
    @DeleteMapping("/role/{id}")
    public ResponseVO deleteRole(@PathVariable("id") Long id) {
        return ResultUtil.success("deleteRole 已执行");
    }
}
