package com.ptteng.academy.persistence.mapper;

import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;
import com.ptteng.academy.persistence.beans.Module;
import com.ptteng.academy.plugin.BaseMapper;

import java.util.List;

/**
 * @program: canary
 * @description: 模块Mapper
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-30 15:57
 **/

public interface ModuleMapper extends BaseMapper<Module> {
    List<ModuleDto> findModuleByName(ModuleQuery moduleQuery);
}
