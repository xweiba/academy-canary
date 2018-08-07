package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.HomeVideoDto;
import com.ptteng.academy.business.dto.SigningDto;
import com.ptteng.academy.business.dto.StudentCardDto;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;

/**
 * description:
 * author:Lin
 * Date:2018/7/29
 * Time:15:19
 */
public interface StudentCardService {
    StudentCardDto selectAll(Long id) throws ResourceIsNullException;

    boolean updateStudentCard(StudentCardQuery studentCardQuery) throws ResourceIsNullException;

    PageInfo findCollectArticle(Long id, Integer pageNum) throws FindNullException;

    PageInfo<HomeVideoDto> findCollectVideo(Long id, Integer pageNum) throws FindNullException;

    SigningDto verifyCode(Long id, String code) throws ResourceIsNullException;

    SigningDto verifyCodePhone(Long id, String code) throws ResourceIsNullException;

}


