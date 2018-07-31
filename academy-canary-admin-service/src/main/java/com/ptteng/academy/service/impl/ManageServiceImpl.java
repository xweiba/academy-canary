package com.ptteng.academy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.persistence.beans.Module;
import com.ptteng.academy.persistence.mapper.ModuleMapper;
import com.ptteng.academy.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public PageInfo<ModuleDto> findModuleByName(ModuleQuery moduleQuery) {
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
}
