package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.RoleQuery;
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
 * @description: 角色
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 09:17
 **/

@Api(tags = "RoleController", description = "角色相关Api")
@RestController
public class RoleController {
    /**
     * @description 获取角色列表
     * @param: []
     */
    @GetMapping("/rolenames")
    public ResponseRowsVO roleNames() {
        List<Object> roleList = new ArrayList<Object>();
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("市场");

        RoleDto roleDto2 = new RoleDto();
        roleDto2.setId(2L);
        roleDto.setName("管理员");

        RoleDto roleDto3 = new RoleDto();
        roleDto3.setId(3L);
        roleDto.setName("运营");
        roleList.add(roleDto);
        roleList.add(roleDto2);
        roleList.add(roleDto3);

        return ResultUtil.success("roleNames 已执行", roleList);
    }

    @PostMapping("/roles")
    public ResponseRowsVO getRoles(RoleQuery roleQuery) {

        List<Object> roleDtoList = new ArrayList<Object>();
        Integer [] nums = {0,1,2,3,4,5,6,7,8,9};
        for (Long i = 1L; i < 10; i++) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(i);
            roleDto.setName("葫芦娃" + i);
            roleDto.setCreateAt(new Date());
            roleDto.setCreateBy("admin" + i);
            roleDto.setModuleIds(nums);
            roleDtoList.add(roleDto);
        }
        return ResultUtil.success("getRoles 已执行", roleDtoList);
    }

    @GetMapping("/role/{id}")
    public ResponseVO getRole(@PathVariable("id") Long id) {
        RoleDto roleDto = new RoleDto();
        Integer [] nums = {0,1,2,3,4,5,6,7,8,9};
        roleDto.setId(id);
        roleDto.setName("葫芦娃" + id);
        roleDto.setCreateAt(new Date());
        roleDto.setCreateBy("admin" + id);
        roleDto.setModuleIds(nums);
        return ResultUtil.success("getRoleName 已执行", roleDto);
    }
    @PostMapping("/role")
    public ResponseVO createRole(RoleDto roleDto) {
        return ResultUtil.success("createRole 已执行", roleDto);
    }

    @PutMapping("/role")
    public ResponseVO updateRole(RoleDto roleDto) {
        return ResultUtil.success("updateRole 已执行", roleDto);
    }

    @DeleteMapping("/role/{id}")
    public ResponseVO deleteRole(@PathVariable("id") Long id) {
        return ResultUtil.success("deleteRole 已执行");
    }
}
