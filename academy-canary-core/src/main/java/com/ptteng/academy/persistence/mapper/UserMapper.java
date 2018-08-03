package com.ptteng.academy.persistence.mapper;

import com.ptteng.academy.business.dto.UserBackDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.plugin.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: canary
 * @description: 用户
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 02:14
 **/
@Repository
public interface UserMapper extends BaseMapper<User>{
    //查询用户信息
    List<UserBackDto> findUser(UserQuery userQuery);
}
