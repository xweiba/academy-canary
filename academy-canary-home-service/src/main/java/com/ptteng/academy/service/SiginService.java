package com.ptteng.academy.service;

import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.vo.ResponseVO;

/**
 * description:
 * author:Lin
 * Date:2018/7/23
 * Time:20:25
 */
public interface SiginService{
    UserDto selectSiginById(Long id);

    ResponseVO sigin(Long id);

}
