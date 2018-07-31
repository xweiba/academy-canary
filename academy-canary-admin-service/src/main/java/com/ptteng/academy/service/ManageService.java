package com.ptteng.academy.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.AccountQuery;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.persistence.beans.Account;

import java.util.List;

/**
 * @program: canary
 * @description: 管理模块
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-30 15:50
 **/

public interface ManageService {
    /* 根据条件查询模块 */
    PageInfo<ModuleDto> findModuleByQuery(ModuleQuery moduleQuery);

    /* 根据id查询模块*/
    ModuleDto findModuleById(Long id);

    /* 删除模块 */
    Boolean deleteModule(Long id);

    /* 更新模块 */
    Boolean updateModule(ModuleDto moduleDto);

    /* 插入模块 */
    Boolean insertModule(ModuleDto moduleDto);


    /* 角色模块 */
    PageInfo<RoleDto> findRoleByQuery(RoleQuery roleQuery);
    List<RoleDto> findRoleNames();
    /*  根据id查询模块 */
    RoleDto findRoleById(Long id);
    /* 删除角色 */
    Boolean deleteRoleById(Long id);
    /* 新增模块 */
    Boolean insertRole(RoleDto roleDto);
    /* 更新模块 */
    Boolean updateRole(RoleDto roleDto);

    /* 账号模块 */
    PageInfo<AccountDto> findAccountByQuery(AccountQuery accountQuery);
    AccountDto findAccountById(Long id);
    Boolean updateAccount(AccountDto accountDto);
    Boolean deleteAccountById(Long id);
    Boolean insertAccount(AccountDto accountDto);


}
