package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.business.dto.UserBackDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.framework.pojo.AbstractService;
import com.ptteng.academy.persistence.beans.Author;

import java.util.List;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-25 19:33
 **/

public interface ConsumeService extends AbstractService<AuthorDto, Long> {
    // 通过作者名称返回作者id
    Long findAuthorByName(String name);
    // 通过作者id返回用户名
    String findAuthorById(Long id);

    //查询用户信息
    PageInfo<UserBackDto> findUser(UserQuery userQuery);
}
