package com.ptteng.academy.service;

import com.ptteng.academy.business.dto.StudentCardDto;

/**
 * description:
 * author:Lin
 * Date:2018/7/29
 * Time:15:19
 */
public interface StudentCardService {
    StudentCardDto selectAll(Long id);
}
