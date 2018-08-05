package com.ptteng.academy.persistence.mapper;

import com.ptteng.academy.business.dto.UserBackDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.plugin.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
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

    // 更新用户状态
    @Update("UPDATE user u SET u.status = !u.status, u.update_at = #{update_at}, u.update_by = #{update_by}  WHERE u.id = #{id}")
    Integer updateUserStatus(@Param("id") Long id, @Param("update_at") Date update_at, @Param("update_by") String update_by);
}
