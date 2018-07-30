package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/modules")
    public ResponseRowsVO getModules(ModuleQuery moduleQuery) {
        List<Object> moduleDtoList = new ArrayList<Object>();
        ModuleDto moduleDto = new ModuleDto();

        moduleDto.setId(1L);
        moduleDto.setName("用户管理");
        moduleDto.setParent_id(0L);
        moduleDto.setCreate_at(new Date());
        moduleDto.setCreate_by("admin");
        moduleDto.setIndex(1);
        moduleDtoList.add(moduleDto);

        ModuleDto moduleDto2 = new ModuleDto();
        moduleDto2.setId(2L);
        moduleDto2.setName("用户列表");
        moduleDto2.setParent_id(1L);
        moduleDto2.setModule_url(".users");
        moduleDto2.setCreate_at(new Date());
        moduleDto2.setCreate_by("admin");
        moduleDto2.setIndex(1);
        moduleDtoList.add(moduleDto2);

        ModuleDto moduleDto3 = new ModuleDto();
        moduleDto3.setId(3L);
        moduleDto3.setName("内容管理");
        moduleDto3.setIndex(2);
        moduleDto3.setParent_id(0L);
        moduleDto3.setCreate_at(new Date());
        moduleDto3.setCreate_by("admin");
        moduleDto3.setIndex(2);
        moduleDtoList.add(moduleDto3);

        ModuleDto moduleDto4 = new ModuleDto();
        moduleDto4.setId(4L);
        moduleDto4.setName("文章列表");
        moduleDto4.setParent_id(3L);
        moduleDto4.setModule_url(".articles");
        moduleDto4.setCreate_at(new Date());
        moduleDto4.setCreate_by("admin");
        moduleDto4.setIndex(1);
        moduleDtoList.add(moduleDto4);

        ModuleDto moduleDto5 = new ModuleDto();
        moduleDto5.setId(5L);
        moduleDto5.setName("视频列表");
        moduleDto5.setParent_id(3L);
        moduleDto5.setModule_url(".videos");
        moduleDto5.setCreate_at(new Date());
        moduleDto5.setCreate_by("admin");
        moduleDto5.setIndex(2);
        moduleDtoList.add(moduleDto5);

        ModuleDto moduleDto6 = new ModuleDto();
        moduleDto6.setId(6L);
        moduleDto6.setName("后台管理");
        moduleDto6.setIndex(999);
        moduleDto6.setParent_id(0L);
        moduleDto6.setCreate_at(new Date());
        moduleDto6.setCreate_by("admin");
        moduleDtoList.add(moduleDto6);

        ModuleDto moduleDto7 = new ModuleDto();
        moduleDto7.setId(7L);
        moduleDto7.setName("账号管理");
        moduleDto7.setParent_id(6L);
        moduleDto7.setModule_url(".account");
        moduleDto7.setCreate_at(new Date());
        moduleDto7.setCreate_by("admin");
        moduleDto7.setIndex(1);
        moduleDtoList.add(moduleDto7);

        ModuleDto moduleDto8 = new ModuleDto();
        moduleDto8.setId(8L);
        moduleDto8.setName("角色管理");
        moduleDto8.setParent_id(6L);
        moduleDto8.setModule_url(".role");
        moduleDto8.setCreate_at(new Date());
        moduleDto8.setCreate_by("admin");
        moduleDto8.setIndex(2);
        moduleDtoList.add(moduleDto8);

        ModuleDto moduleDto9 = new ModuleDto();
        moduleDto9.setId(9L);
        moduleDto9.setName("修改密码");
        moduleDto9.setParent_id(6L);
        moduleDto9.setModule_url(".password");
        moduleDto9.setCreate_at(new Date());
        moduleDto9.setCreate_by("admin");
        moduleDto9.setIndex(3);
        moduleDtoList.add(moduleDto9);

        ModuleDto moduleDto10 = new ModuleDto();
        moduleDto10.setId(10L);
        moduleDto10.setName("模块管理");
        moduleDto10.setParent_id(6L);
        moduleDto10.setModule_url(".module");
        moduleDto10.setCreate_at(new Date());
        moduleDto10.setCreate_by("admin");
        moduleDto10.setIndex(4);
        moduleDtoList.add(moduleDto10);

        return ResultUtil.success("getModules 已执行", moduleDtoList);
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
