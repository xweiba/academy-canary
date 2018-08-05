package com.ptteng.academy.controller;


import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.ConsumeService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: canary
 * @description: 个人信息
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 03:06
 **/
@Slf4j
@Api(tags = "ConsumerController", description = "微信用户/作者相关Api")
@RestController
public class ConsumerController {
    @Resource
    private ConsumeService consumeService;
    @Resource
    private UserMapper userMapper;


    @ApiOperation(value = "获取作者信息", notes = "返回所有作者信息")
    @GetMapping("/authors")
    public ResponseRowsVO getAuthors() throws Exception {
        List<AuthorDto> authorDtoList = consumeService.listAll();
        return ResultUtil.success("作者信息获取成功", authorDtoList);
    }

    @ApiOperation(value = "获取作者详细信息", notes = "根据作者id获取其详细信息")
    @ApiImplicitParam(name = "id", value = "作者id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/author/{id}")
    public ResponseVO getAuthor(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("作者信息获取成功", consumeService.getByPrimaryKey(id));
    }


    @RequiresPermissions(".videos")
    @ApiOperation(value = "删除作者", notes = "根据作者id删除")
    @ApiImplicitParam(name = "id", value = "作者id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @DeleteMapping("/author/{id}")
    public ResponseVO deleteAuthor(@PathVariable("id") Long id) throws Exception {
        consumeService.removeByPrimaryKey(id);
        return ResultUtil.success("删除作者成功");
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "创建作者信息", notes = "创建作者")
    @PostMapping("/author")
    public ResponseVO createAuthor(@RequestBody AuthorDto authorDto) throws Exception {
        AuthorDto result = consumeService.insert(authorDto);
        return ResultUtil.success("创建作者成功", result);
    }

    @RequiresPermissions(".user")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @PostMapping("/users")
    public ResponseRowsVO queryUser(@RequestBody UserQuery userQuery) throws Exception {
        return ResultUtil.success("获取用户列表成功", consumeService.findUser(userQuery));
    }

    @RequiresPermissions(".user")
    @ApiOperation(value = "改变用户状态", notes = "根据ID改变用户状态")
    @PutMapping("/user/{id}/status")
    public ResponseVO setStatus(@PathVariable("id") Long id) throws Exception {
/*        User user = userMapper.selectByPrimaryKey(id);
        user.setStatus(!user.getStatus());
        log.info("改变用户状态" + user);*/
        return ResultUtil.success("用户冻结/解冻成功", consumeService.updateUserStatus(id));
    }

    @ApiOperation(value = "获取用户详情", notes = "根据ID获取用户详情")
    @RequiresPermissions(".user")
    @GetMapping("/user/{id}")
    public ResponseVO getUser(@PathVariable("id") Long id) throws Exception {
        return ResultUtil.success("获取用户详情成功", consumeService.findUserById(id));
    }
}
