package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: canary
 * @description: 模块
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 01:57
 **/

@Slf4j
@Api(tags = "ModuleController", description = "模块相关Api")
@RestController
public class ModuleController {

    @Resource
    ManageService manageService;

    /* 这里不能加权限 所有登陆用户都需要获取模块列表 */
    @RequiresPermissions(".module")
    @ApiOperation(value = "获取模块信息列表", notes = "按id排序")
    @PostMapping("/modules")
    public ResponseRowsVO getModules(ModuleQuery moduleQuery) {
        return ResultUtil.success("getModules 已执行", manageService.findModuleByQuery(moduleQuery));
    }
    /* 这里不能加权限 所有登陆用户都需要获取模块列表 */
    @ApiOperation(value = "获取角色所有模块信息", notes = "按id排序")
    @PostMapping("/accountmodules")
    public ResponseRowsVO getAccountModules() {
        return ResultUtil.success("成功获取用户模块", manageService.findAccountModules());
    }
    @RequiresPermissions(".module")
    @ApiOperation(value = "根据id获取模块信息", notes = "返回模块信息")
    @ApiImplicitParam(name = "id", value = "模块Id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/module/{id}")
    public ResponseVO getModule(@PathVariable("id") Long id){
        return ResultUtil.success("获取模块信息成功", manageService.findModuleById(id));
    }

    @RequiresPermissions(".module")
    @ApiOperation(value = "根据id删除", notes = "执行成功返回true")
    @ApiImplicitParam(name = "id", value = "模块Id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @DeleteMapping("/module/{id}")
    public ResponseVO deleteModule(@PathVariable("id") Long id) {
        try {
            Boolean module = manageService.deleteModule(id);
            return ResultUtil.success("删除模块成功", module);
        } catch (Exception e){
            return ResultUtil.error("删除模块失败, 该模块不存在!");
        }
    }

    @ApiOperation(value = "根据 id 更新模块信息", notes = "执行成功返回true")
    @ApiImplicitParam(name = "id", value = "模块Id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @PutMapping("/module/{id}")
    public ResponseVO updateModule(@PathVariable("id") Long id, @RequestBody ModuleDto moduleDto) {
        moduleDto.setId(id);
        log.info("updateModule:", moduleDto.toString());
        return ResultUtil.success("updateModule 已执行", manageService.updateModule(moduleDto));
    }
    @ApiOperation(value = "创建新模块", notes = "执行成功返回true")
    @PostMapping("/module")
    public ResponseVO createModule(@RequestBody ModuleDto moduleDto) {
        return ResultUtil.success("createModule 已执行", manageService.insertModule(moduleDto));
    }
}
