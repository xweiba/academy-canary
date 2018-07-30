package com.ptteng.academy.service.Impl;

import com.ptteng.academy.business.Converfor;
import com.ptteng.academy.business.dto.StudentCardDto;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.StudentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:学生证
 * author:Lin
 * Date:2018/7/29
 * Time:15:20
 */
@Service
public class StudentCardServiceImpl implements StudentCardService {
    @Autowired
    UserMapper userMapper;

    //学生证资料
    @Override
    public StudentCardDto selectAll(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return Converfor.UserDoToStudentCartDtoConvert(user);
    }
}
