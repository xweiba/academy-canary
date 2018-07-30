package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 模块
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-21 01:57
 **/

@Api(tags = "ModuleController", description = "模块相关Api")
@RestController
public class ModuleController {

    @Resource
    ManageService manageService;

    @PostMapping("/modules")
    public ResponseRowsVO getModules(ModuleQuery moduleQuery) {
        return ResultUtil.success("getModules 已执行", manageService.findModuleByName(moduleQuery));
    }

    @GetMapping("/module/{id}")
    public ResponseVO getModule(@PathVariable("id") Long id){
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setId(id);
        moduleDto.setName("用户列表");
        moduleDto.setParent_id(1L);
        moduleDto.setModule_url("/users");

        return ResultUtil.success("getModule 已执行", moduleDto);
    }
    @DeleteMapping("/module/{id}")
    public ResponseVO deleteModule(@PathVariable("id") Long id) {
        return ResultUtil.success("deleteModule 已执行");
    }
    @PutMapping("/module")
    public ResponseVO updateModule(ModuleDto moduleDto) {
        return ResultUtil.success("updateModule 已执行", moduleDto);
    }

    @PostMapping("/module")
    public ResponseVO createModule(ModuleDto moduleDto) {
        return ResultUtil.success("createModule 已执行", moduleDto);
    }
}
