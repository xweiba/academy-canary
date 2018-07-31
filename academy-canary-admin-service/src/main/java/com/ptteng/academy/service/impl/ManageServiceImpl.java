package com.ptteng.academy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.dto.StudyDto;
import com.ptteng.academy.business.query.AccountQuery;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.persistence.beans.Account;
import com.ptteng.academy.persistence.beans.Module;
import com.ptteng.academy.persistence.beans.Role;
import com.ptteng.academy.persistence.mapper.AccountMapper;
import com.ptteng.academy.persistence.mapper.ModuleMapper;
import com.ptteng.academy.persistence.mapper.RoleMapper;
import com.ptteng.academy.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description: 管理模块实现
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-30 15:55
 **/

@Slf4j
@Service
public class ManageServiceImpl implements ManageService {
    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AccountMapper accountMapper;

    @Override
    public PageInfo<ModuleDto> findModuleByQuery(ModuleQuery moduleQuery) {
        PageHelper.startPage(10,10);
        List<ModuleDto> moduleDtoList = moduleMapper.findModuleByName(moduleQuery);
        return new PageInfo<ModuleDto>(moduleDtoList);
    }

    @Override
    public ModuleDto findModuleById(Long id) {
        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(moduleMapper.selectByPrimaryKey(id), moduleDto);
        return moduleDto;
    }

    @Override
    public Boolean deleteModule(Long id) {
        return moduleMapper.deleteByPrimaryKey(id) > 0 ;
    }

    @Override
    public Boolean updateModule(ModuleDto moduleDto) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setUpdate_at(new Date());
        module.setUpdate_by("admin");
        return moduleMapper.updateByPrimaryKeySelective(module) > 0;
    }

    @Override
    public Boolean insertModule(ModuleDto moduleDto) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setCreate_at(new Date());
        module.setCreate_by("admin");
        return moduleMapper.insert(module) > 0;
    }

    @Override
    public PageInfo<RoleDto> findRoleByQuery(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<RoleDto> roleList =  roleMapper.findRoleByQuery(roleQuery);
        PageInfo bean = new PageInfo<RoleDto>(roleList);

        return bean;
    }

    @Override
    public List<RoleDto> findRoleNames() {
        List<RoleDto> roleDtoList = roleMapper.findRoleNames();
        return roleDtoList;
    }

    @Override
    public RoleDto findRoleById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteRoleById(Long id) {
        return moduleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Boolean insertRole(RoleDto roleDto) {
        return null;
    }

    @Override
    public Boolean updateRole(RoleDto roleDto) {
        return null;
    }

    /* 账号模块 */
    @Override
    public PageInfo<AccountDto> findAccountByQuery(AccountQuery accountQuery) {
        PageHelper.startPage(accountQuery.getPageNum(), accountQuery.getPageSize());
        List<AccountDto> accountDtoList = accountMapper.findAccountByQuery(accountQuery);
        PageInfo<AccountDto> pageInfo = new PageInfo<AccountDto>(accountDtoList);
        return pageInfo;
    }

    @Override
    public AccountDto findAccountById(Long id) {
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(accountMapper.selectByPrimaryKey(id), accountDto);
        return accountDto;
    }

    @Override
    public Boolean updateAccount(Account account) {
        return null;
    }

    @Override
    public Boolean deleteAccountById(Long id) {
        return null;
    }

    @Override
    public Boolean insertAccount(Account account) {
        return null;
    }
}
