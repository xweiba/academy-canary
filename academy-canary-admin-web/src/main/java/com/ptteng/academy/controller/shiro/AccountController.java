package com.ptteng.academy.controller.shiro;

import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.query.AccountQuery;
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
 * @description: 账号
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 09:18
 **/

@Api(tags = "AccountController", description = "账号相关Api")
@RestController
public class AccountController {


    @Resource
    private ManageService manageService;

    /**
     * @description 分页查询用户信息
     * @param: [accountQuery]
     */
    @PostMapping("/accounts")
    public ResponseRowsVO getAccounts(AccountQuery accountQuery) {
        return ResultUtil.success("getAccounts 已执行", manageService.findAccountByQuery(accountQuery));
    }
    
    /**
     * @description 根据id获取用户
     * @param: [id]
     */
    @GetMapping("/account/{id}")
    public ResponseVO getAccount(@PathVariable("id") String id) {
        AccountDto accountDto = new AccountDto();

        accountDto.setRole_name("管理员" + id);
        accountDto.setUsername("葫芦娃" + id);

        return ResultUtil.success("getAccount 已执行", accountDto);
    }
    
    /**
     * @description 创建
     * @param: [accountDto]
     */
    @PostMapping("/account")
    public ResponseVO createAccount(AccountDto accountDto) {
        return ResultUtil.success("createAccount 已执行", accountDto);
    }

    /**
     * @description 更新账号
     * @param: [accountDto]
     */
    @PutMapping("/account/{id}")
    public ResponseVO updateAccount(@PathVariable("id") Long id, AccountDto accountDto) {
        accountDto.setId(id);
        return ResultUtil.success("updateAccount 已执行", accountDto);
    }

    @DeleteMapping("/account/{id}")
    public ResponseVO deleteAccount(@PathVariable("id") Long id) {
        return ResultUtil.success("deleteAccount 已执行");
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
