package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.ModuleDto;
import com.ptteng.academy.business.query.ModuleQuery;

/**
 * @program: canary
 * @description: 管理模块
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-30 15:50
 **/

public interface ManageService {
    /* 根据条件查询模块 */
    PageInfo<ModuleDto> findModuleByName(ModuleQuery moduleQuery);

    ModuleDto findModuleById(Long id);

}
