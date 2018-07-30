package com.ptteng.academy.persistence.mapper;

import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.persistence.beans.Signin;
import com.ptteng.academy.plugin.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * description:
 * author:Lin
 * Date:2018/7/25
 * Time:18:10
 */

@Mapper
@Repository
public interface LoginMapper extends BaseMapper<Signin> {
    @Select("SELECT user.head_img_url,user.nick_name,user.bean,signin.* FROM user,signin WHERE user.id = signin.id AND user.id = #{id}")
    UserDto findUserById(@Param("id")Long id);

    @Select("SELECT * FROM Signin WHERE id = #{id}")
    Signin findSiginById(Long id);
}
