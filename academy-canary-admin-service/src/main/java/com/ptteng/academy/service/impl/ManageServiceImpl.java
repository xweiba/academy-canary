package com.ptteng.academy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.persistence.mapper.ModuleMapper;
import com.ptteng.academy.service.ManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: canary
 * @description: 管理模块实现
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-30 15:55
 **/
@Service
public class ManageServiceImpl implements ManageService {
    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public PageInfo<ModuleDto> findModuleByName(ModuleQuery moduleQuery) {
        PageHelper.startPage(10,10);
        List<ModuleDto> moduleDtoList = moduleMapper.findModuleByName(moduleQuery.getModuleName());

        return new PageInfo<ModuleDto>(moduleDtoList);
    }
}
