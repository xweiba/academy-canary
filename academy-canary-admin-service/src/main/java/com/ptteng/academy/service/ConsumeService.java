package com.ptteng.academy.service;

import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.business.dto.UserBackDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.framework.pojo.AbstractService;
import com.ptteng.academy.persistence.beans.User;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-25 19:33
 **/

public interface ConsumeService extends AbstractService<AuthorDto, Long> {
    // 通过作者名称返回作者id
    Long findAuthorByName(String name) throws Exception;

    // 通过作者id返回用户名
    String findAuthorById(Long id) throws Exception;

    //查询用户信息
    PageInfo<UserBackDto> findUser(UserQuery userQuery) throws Exception;

    // 获取枚举列表
    List<Object> findListByName(String name) throws Exception;

    // 更新用户状态
    Integer updateUserStatus(Long id) throws Exception;
    UserDto findUserById(Long id) throws Exception;
}
