package com.ptteng.academy.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.persistence.beans.Author;
import com.ptteng.academy.service.ConsumeService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: canary
 * @description: 个人信息
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 03:06
 **/

@Api(tags = "ConsumerController", description = "微信用户/作者相关Api")
@RestController
public class ConsumerController {
    @Resource
    private ConsumeService consumeService;
    private Boolean status = false; // 状态


    @ApiOperation(value = "获取作者信息", notes = "返回所有作者信息")
    @GetMapping("/authors")
    public ResponseRowsVO getAuthors() {
        List<AuthorDto> authorDtoList = consumeService.listAll();
        return ResultUtil.success("作者信息获取成功", authorDtoList);
    }

    @ApiOperation(value = "获取作者详细信息", notes = "根据作者id获取其详细信息")
    @ApiImplicitParam(name = "id", value = "作者id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/author/{id}")
    public ResponseVO getAuthor(@PathVariable("id") Long id) {
        return ResultUtil.success("作者信息获取成功", consumeService.getByPrimaryKey(id));
    }


    @RequiresPermissions(".videos")
    @ApiOperation(value = "删除作者", notes = "根据作者id删除")
    @ApiImplicitParam(name = "id", value = "作者id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @DeleteMapping("/author/{id}")
    public ResponseVO deleteAuthor(@PathVariable("id") Long id) {
        consumeService.removeByPrimaryKey(id);
        return ResultUtil.success("删除作者成功");
    }

    @RequiresPermissions(".videos")
    @ApiOperation(value = "创建作者信息", notes = "创建作者")
    @PostMapping("/author")
    public ResponseVO createAuthor(@RequestBody AuthorDto authorDto) {
        try {
            AuthorDto result = consumeService.insert(authorDto);
            return ResultUtil.success("创建作者成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("创建作者失败, 作者姓名不能为空");
        }
    }

    @RequiresPermissions(".user")
    // 获取用户信息
    @PostMapping("/users")
    public ResponseRowsVO queryUser(@RequestBody UserQuery userQuery) {
        List<Object> users = new ArrayList<Object>();
        for (Long i = 1L; i <= userQuery.getPageSize(); i++) {
            UserDto userDto = new UserDto();
            userDto.setId(i);
            userDto.setNickName("第" + userQuery.getPageNum() + "昵称" + i);
            userDto.setEmail("173828" + i + "@qq.com");
            userDto.setPhone(138967001451L + i);
            userDto.setBean(Math.toIntExact(i));
            userDto.setGrade("一年级");
            userDto.setPrefecture("北京");
            userDto.setStatus(true);
            users.add(userDto);
        }
        return ResultUtil.success("queryUser 调用成功", users);
    }

    @RequiresPermissions(".user")
    @PutMapping("/user/{id}/status")
    public ResponseVO setStatus(@PathVariable("id") Integer id) {
        /*  赋值时 !Status 即可 */
        return ResultUtil.success("status 调用成功", !status);
    }

    @RequiresPermissions(".user")
    @GetMapping("/user/{id}")
    public ResponseVO getUser(@PathVariable("id") Integer id) {
        UserDto getUserDto = new UserDto();
        getUserDto.setId(12L);
        getUserDto.setNickName("昵称");
        getUserDto.setEmail("173828" + "@qq.com");
        getUserDto.setGrade("二年级");
        getUserDto.setPhone(17555551234L);
        getUserDto.setPrefecture("重庆");
        getUserDto.setBean(45);
        getUserDto.setHeadImgUrl("http://93.179.100.194:8080/hand.jpg");
        return ResultUtil.success("getUser 调用成功", getUserDto);
    }
}
