package com.ptteng.academy.service;

import com.ptteng.academy.business.dto.SigningDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.framework.exception.ResourceIsNullException;

/**
 * description:
 * author:Lin
 * Date:2018/7/23
 * Time:20:25
 */
public interface SiginService{
    UserDto selectSiginById(Long id) throws ResourceIsNullException;

    SigningDto sigin(Long id) throws ResourceIsNullException;

}
