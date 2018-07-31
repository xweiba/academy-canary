package com.ptteng.academy.service;

import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.framework.pojo.AbstractService;
import com.ptteng.academy.persistence.beans.Author;

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
}
