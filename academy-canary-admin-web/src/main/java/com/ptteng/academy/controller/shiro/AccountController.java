package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.query.AccountQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 账号
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 09:18
 **/

@Slf4j
@Api(tags = "AccountController", description = "账号相关Api")
@RestController
public class AccountController {


    @Resource
    private ManageService manageService;

    /**
     * @description 分页查询用户信息
     * @param: [accountQuery]
     */
    @ApiOperation(value = "根据条件获取账号信息", notes = "执行成功返回账号列表")
    @PostMapping("/accounts")
    public ResponseRowsVO getAccounts(@RequestBody AccountQuery accountQuery) {
        return ResultUtil.success("获取账号列表成功", manageService.findAccountByQuery(accountQuery));
    }
    
    /**
     * @description 根据id获取用户
     * @param: [id]
     */
    @ApiOperation(value = "根据条件获取账号信息", notes = "执行成功返回账号列表信息")
    @ApiImplicitParam(name = "id", value = "账号id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @GetMapping("/account/{id}")
    public ResponseVO getAccount(@PathVariable("id") Long id) {
        return ResultUtil.success("获取账号信息成功", manageService.findAccountById(id));
    }
    
    /**
     * @description 创建
     * @param: [accountDto]
     */
    @ApiOperation(value = "创建账号信息", notes = "执行成功返回true")
    @PostMapping("/account")
    public ResponseVO createAccount(@RequestBody AccountDto accountDto) {
        log.debug("accountDto.toString(): " + accountDto.toString());
        return ResultUtil.success("createAccount 已执行", manageService.insertAccount(accountDto));
    }

    /**
     * @description 更新账号
     * @param: [accountDto]
     */
    @ApiOperation(value = "根据 id 更新账号信息", notes = "执行成功返回true")
    @ApiImplicitParam(name = "id", value = "账号id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @PutMapping("/account/{id}")
    public ResponseVO updateAccount(@PathVariable("id") Long id, @RequestBody AccountDto accountDto) {
        accountDto.setId(id);
        return ResultUtil.success("更新成功", accountDto);
    }

    @ApiOperation(value = "根据 id 删除账号信息", notes = "执行成功返回true")
    @ApiImplicitParam(name = "id", value = "账号id", required = true, paramType = "path", dataType = "Long", defaultValue = "1")
    @DeleteMapping("/account/{id}")
    public ResponseVO deleteAccount(@PathVariable("id") Long id) {
        return ResultUtil.success("删除成功", manageService.deleteAccountById(id));
    }

    /**
     * @description 更新密码
     * @param: [accountDto]
     */
    @PutMapping("/account/password")
    public ResponseVO restAccount(AccountDto accountDto){
        System.out.println(accountDto.toString());
        return ResultUtil.success("restAccount 已执行");
    }
}
