package com.ptteng.academy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.dto.RoleDto;
import com.ptteng.academy.business.query.AccountQuery;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.business.query.RoleQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.beans.Account;
import com.ptteng.academy.persistence.beans.Module;
import com.ptteng.academy.persistence.beans.Role;
import com.ptteng.academy.persistence.mapper.AccountMapper;
import com.ptteng.academy.persistence.mapper.ModuleMapper;
import com.ptteng.academy.persistence.mapper.RoleMapper;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.util.PasswordUtil;
import com.ptteng.academy.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
    public PageInfo<ModuleDto> findModuleByQuery(ModuleQuery moduleQuery) throws Exception {
        PageHelper.startPage(moduleQuery.getPageNum(), moduleQuery.getPageSize());
        moduleQuery.setRole_id(getOnlineAccount().getRole_id());
        List<ModuleDto> moduleDtoList = moduleMapper.findModuleByName(moduleQuery);
        if (moduleDtoList.isEmpty()) {
            throw new FindNullException();
        }
        log.debug(JSONObject.toJSONString(moduleDtoList));
        return new PageInfo<ModuleDto>(moduleDtoList);
    }

    @Override
    public ModuleDto findModuleById(Long id) throws Exception {
        Module module = moduleMapper.selectByPrimaryKey(id);
        if (module == null) {
            throw new ResourceIsNullException();
        }
        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(module, moduleDto);
        return moduleDto;
    }

    @Override
    public Boolean deleteModule(Long id) throws Exception {
        // 先删除关系表
        moduleMapper.deleteRoleModulesByModuleId(id);
        int deleteInt = moduleMapper.deleteByPrimaryKey(id);
        if (deleteInt == 0) {
            throw new ResourceIsNullException();
        }
        return true;
    }

    @Override
    public Boolean updateModule(ModuleDto moduleDto) throws Exception {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setUpdate_at(new Date());
        module.setUpdate_by(getOnlineAccount().getUsername());
        int updateStatus = moduleMapper.updateByPrimaryKeySelective(module);
        if (updateStatus == 0) {
            throw new ResourceIsNullException();
        }
        return true;
    }

    /**
     * @description 新增模块时, 需要添加模块与角色关系表
     * @param: [moduleDto]
     */
    @Override
    public Boolean insertModule(ModuleDto moduleDto) throws Exception {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setUpdate_at(new Date());
        module.setUpdate_by(getOnlineAccount().getUsername());
        module.setCreate_at(new Date());
        module.setCreate_by(getOnlineAccount().getUsername());

        moduleMapper.insert(module);
        log.debug("新增模块id" + module.getId());
        try {
            // 添加当前用户权限
            roleMapper.insertRoleModulesByRoleId(getOnlineAccount().getRole_id(), module.getId());
            // 添加管理员和超级管理员的权限
            roleMapper.insertRoleModulesByRoleId(1L, module.getId());
            roleMapper.insertRoleModulesByRoleId(2L, module.getId());
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            log.debug("当前用户是管理员或超级管理员");
        }
        return true;
    }

    @Override
    public List<ModuleDto> findAccountModules() throws Exception {
        ModuleQuery moduleQuery = new ModuleQuery();
        moduleQuery.setPageSize(9999);
        moduleQuery.setRole_id(getOnlineAccount().getRole_id());
        List<ModuleDto> moduleDtoList = moduleMapper.findModuleByName(moduleQuery);
        if (moduleDtoList.isEmpty()) {
            throw new FindNullException();
        }
        return moduleDtoList;
    }

    @Override
    public PageInfo<RoleDto> findRoleByQuery(RoleQuery roleQuery) throws Exception {
        PageHelper.startPage(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<RoleDto> roleList = roleMapper.findRoleByQuery(roleQuery);
        if (roleList.isEmpty()) {
            throw new FindNullException();
        }
        return new PageInfo<>(roleList);
    }

    @Override
    public List<RoleDto> findRoleNames() throws Exception {
        List<RoleDto> roleDtoList = roleMapper.findRoleNames();
        if (roleDtoList.isEmpty()) {
            throw new FindNullException();
        }
        return roleDtoList;
    }

    @Override
    public RoleDto findRoleById(Long id) throws Exception {
        RoleDto roleDto = roleMapper.findRoleById(id);
        if (roleDto==null) {
            throw new ResourceIsNullException();
        }
        List<Long> modules = roleMapper.findRoleModuleById(id);
        roleDto.setModuleIds(modules);
        return roleDto;
    }

    @Override
    public Boolean deleteRoleById(Long id) throws Exception {
        // 注意角色与账号绑定的, 如果需要删除角色必须先取消账号的关联
        // 先删除关系表
        roleMapper.deleteRoleModulesByRoleId(id);
        Integer deleteStatus = roleMapper.deleteByPrimaryKey(id);
        if (deleteStatus == 0){
            throw new ResourceIsNullException();
        }
        return true;
    }

    @Override
    public RoleDto insertRole(RoleDto roleDto) throws Exception {
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
        // 前台只要id和角色名称
        roleDto.setId(role.getId());
        roleDto.setRole_name(role.getRole_name());
        roleDto.setModuleIds(null);
        return roleDto;
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) throws Exception {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        role.setUpdate_at(new Date());
        role.setUpdate_by(getOnlineAccount().getUsername());
        roleMapper.updateByPrimaryKeySelective(role);
        Long roleId = role.getId();
        // 2. 关联模块, 先删除原有的, 然后遍历插入
        roleMapper.deleteRoleModulesByRoleId(roleId);
        List<Long> moduleList = new ArrayList<>();
        for (Long moduleId :
                roleDto.getModuleIds()) {
            try {
                roleMapper.insertRoleModulesByRoleId(roleId, moduleId);
                moduleList.add(moduleId);
            } catch (DataIntegrityViolationException e) {
                log.debug("忽略部分错误id和原有id");
                e.printStackTrace();
            }
        }
        roleDto.setModuleIds(moduleList);
        return roleDto;
    }

    /* 账号模块 */
    @Override
    public PageInfo<AccountDto> findAccountByQuery(AccountQuery accountQuery) throws Exception {
        PageHelper.startPage(accountQuery.getPageNum(), accountQuery.getPageSize());
        List<AccountDto> accountDtoList = accountMapper.findAccountByQuery(accountQuery);
        if (accountDtoList.isEmpty()) {
            throw new FindNullException();
        }
        return new PageInfo<>(accountDtoList);
    }

    // 验证账号密码
    @Override
    public Boolean findAccountByPassword(String passWord) throws Exception {
        return accountMapper.findAccountByPassword(getOnlineAccount().getId(), passWord);
    }

    @Override
    public AccountDto findAccountById(Long id) throws Exception {
        AccountDto accountDto = accountMapper.findAccountById(id);
        if (accountDto == null) {
            throw new FindNullException();
        }
        return accountDto;
    }

    @Override
    public Account findAccountAllById(Long id) throws Exception {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public AccountDto findAccountLoginById(String accountName) throws Exception {
        return accountMapper.findAccountLoginById(accountName);
    }

    @Override
    public AccountDto findAccountByUsername(String name) throws Exception {
        return accountMapper.findAccountByName(name);
    }

    @Override
    public Boolean updateAccount(AccountDto accountDto) throws Exception {
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        // 判断是否更新密码
        if (accountDto.getPassword() != null && !accountDto.getPassword().equals("")) {
            account.setPassword(PasswordUtil.encrypt(accountDto.getPassword(), accountDto.getUsername()));
        }
        account.setUpdate_by(getOnlineAccount().getUsername());
        account.setUpdate_at(new Date());
        log.debug("更新账号:" + account.toString());
        int updateStatus = accountMapper.updateByPrimaryKeySelective(account);

        if (updateStatus == 0) {
            throw new ResourceIsNullException();
        }
        return true;
    }

    @Override
    public Boolean deleteAccountById(Long id) throws Exception {
        if (accountMapper.deleteByPrimaryKey(id) == 0) {
            throw new ResourceIsNullException();
        }
        return true;
    }

    @Override
    public Boolean insertAccount(AccountDto accountDto) throws Exception {
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
    public AccountDto getOnlineAccount() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        return (AccountDto) subject.getPrincipal();
    }

    @Override
    public Boolean updatePassWord(AccountDto accountDto) throws Exception {
        log.info("updatePassword: " + accountDto);
        String onlineUserName = getOnlineAccount().getUsername();
        log.debug("修改密码用户:" + onlineUserName);
        String oldPassWord = PasswordUtil.encrypt(accountDto.getOldPassword(), onlineUserName);
        log.debug("oldPassWord:" + oldPassWord);
        if (findAccountByPassword(oldPassWord)) {
            log.debug("更新密码: " + accountDto.toString());
            // 修改密码只能修改当前登陆用户的密码
            Account account = new Account();
            account.setId(getOnlineAccount().getId());
            account.setPassword(PasswordUtil.encrypt(accountDto.getPassword(), onlineUserName));
            account.setUpdate_at(new Date());
            account.setUpdate_by(onlineUserName);
            return accountMapper.updateByPrimaryKeySelective(account) > 0;
        }
        return false;
    }
}
