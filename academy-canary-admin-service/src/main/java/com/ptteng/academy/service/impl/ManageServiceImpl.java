package com.ptteng.academy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.dto.RoleDto;
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
import com.ptteng.academy.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
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
        PageHelper.startPage(10, 10);
        try {
            moduleQuery.setRole_id(getOnlineAccount().getRole_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ModuleDto> moduleDtoList = moduleMapper.findModuleByName(moduleQuery);
        return new PageInfo<ModuleDto>(moduleDtoList);
    }

    @Override
    public ModuleDto findModuleById(Long id) {
        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(moduleMapper.selectByPrimaryKey(id), moduleDto);
        return moduleDto;
    }

    // 添加事务
    @Override
    public Boolean deleteModule(Long id) {
        // 先删除关系表
        moduleMapper.deleteRoleModulesByModuleId(id);
        return moduleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Boolean updateModule(ModuleDto moduleDto) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setUpdate_at(new Date());
        module.setUpdate_by(getOnlineAccount().getUsername());
        return moduleMapper.updateByPrimaryKeySelective(module) > 0;
    }

    /**
     * @description 新增模块时, 需要添加模块与角色关系表
     * @param: [moduleDto]
     */
    @Override
    public Boolean insertModule(ModuleDto moduleDto) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setUpdate_at(new Date());
        module.setUpdate_by(getOnlineAccount().getUsername());
        module.setCreate_at(new Date());
        module.setCreate_by(getOnlineAccount().getUsername());
        try {
            moduleMapper.insert(module);
            log.debug("新增模块id" + module.getId());
            try {
                // 添加当前用户权限
                roleMapper.insertRoleModulesByRoleId(getOnlineAccount().getRole_id(), module.getId());
                // 添加管理员和超级管理员的权限
                roleMapper.insertRoleModulesByRoleId(1L, module.getId());
                roleMapper.insertRoleModulesByRoleId(2L, module.getId());
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("当前用户是管理员或超级管理员");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("添加失败");
        }
        return false;
    }

    @Override
    public PageInfo<RoleDto> findRoleByQuery(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<RoleDto> roleList = roleMapper.findRoleByQuery(roleQuery);
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
        List<Long> modules = roleMapper.findRoleModuleById(id);
        RoleDto roleDto = roleMapper.findRoleById(id);
        roleDto.setModuleIds(modules);
        return roleDto;
    }

    @Override
    public Boolean deleteRoleById(Long id) throws Exception {
        // 注意角色与账号绑定的, 如果需要删除角色必须先取消账号的关联
        // 先删除关系表
        roleMapper.deleteRoleModulesByRoleId(id);
        return roleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public RoleDto insertRole(RoleDto roleDto) {
        // 1. 插入角色
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setCreate_at(new Date());
        role.setUpdate_at(new Date());
        role.setCreate_by(getOnlineAccount().getUsername());
        role.setUpdate_by(getOnlineAccount().getUsername());
        // 返回用户id
        roleMapper.insert(role);
        Long roleId = role.getId();
        log.debug("roleId:" + role.getId());
        // 2. 关联模块, 遍历插入
        for (Long ModuleId :
                roleDto.getModuleIds()) {
            try {
                roleMapper.insertRoleModulesByRoleId(roleId, ModuleId);
            } catch (Exception e) {
                log.error("部分id错误");
                e.printStackTrace();
            }
        }
        roleDto.setId(role.getId());
        roleDto.setRole_name(role.getRole_name());
        roleDto.setModuleIds(null);
        return roleDto;
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setUpdate_at(new Date());
        role.setUpdate_by(getOnlineAccount().getUsername());
        roleMapper.updateByPrimaryKeySelective(role);
        Long roleId = role.getId();
        // 2. 关联模块, 先删除原有的, 然后遍历插入
        // roleMapper.deleteRoleModulesByRoleId(roleId);
        for (Long ModuleId :
                roleDto.getModuleIds()) {
            try {
                roleMapper.insertRoleModulesByRoleId(roleId, ModuleId);
            } catch (Exception e) {
                log.debug("忽略部分错误id和原有id");
                e.printStackTrace();
            }
        }
        return roleDto;
    }

    /* 账号模块 */
    @Override
    public PageInfo<AccountDto> findAccountByQuery(AccountQuery accountQuery) {
        PageHelper.startPage(accountQuery.getPageNum(), accountQuery.getPageSize());
        List<AccountDto> accountDtoList = accountMapper.findAccountByQuery(accountQuery);
        PageInfo<AccountDto> pageInfo = new PageInfo<AccountDto>(accountDtoList);
        return pageInfo;
    }

    // 验证账号密码
    @Override
    public Boolean findAccountByPassword(String passWord) {
        return accountMapper.findAccountByPassword(getOnlineAccount().getId(), passWord);
    }

    @Override
    public AccountDto findAccountById(Long id) {
        return accountMapper.findAccountById(id);
    }

    @Override
    public AccountDto findAccountLoginById(String accountName) {
        return accountMapper.findAccountLoginById(accountName);
    }

    @Override
    public AccountDto findAccountByUsername(String name) {
        return accountMapper.findAccountByName(name);
    }

    @Override
    public Boolean updateAccount(AccountDto accountDto) throws Exception{
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        account.setPassword(PasswordUtil.encrypt(accountDto.getPassword(), accountDto.getUsername()));
        account.setUpdate_by(getOnlineAccount().getUsername());
        account.setUpdate_at(new Date());
        account.setId(getOnlineAccount().getId());
        log.debug("更新账号:" + account.toString());
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    @Override
    public Boolean deleteAccountById(Long id) {
        return accountMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Boolean insertAccount(AccountDto accountDto) throws Exception{
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        account.setPassword(PasswordUtil.encrypt(accountDto.getPassword(), accountDto.getUsername()));
        account.setCreate_at(new Date());
        account.setUpdate_at(new Date());
        account.setCreate_by(getOnlineAccount().getUsername());
        account.setUpdate_by(getOnlineAccount().getUsername());
        log.debug("新增账号account: " + account.toString());
        return accountMapper.insert(account) > 0;
    }

    // 通过SecurityUtils获取当前登陆的账号信息
    @Override
    public AccountDto getOnlineAccount() {
        Subject subject = SecurityUtils.getSubject();
        return (AccountDto) subject.getPrincipal();
    }
}
