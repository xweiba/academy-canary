package com.ptteng.academy.business;


import com.ptteng.academy.business.dto.StudentCardDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.persistence.beans.User;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

/**
 * description:
 * author:Lin
 * Date:2018/7/25
 * Time:13:30
 */
public class Converfor{
    public static User UserDtoToUserDoConvert(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return user;
    }

    public static UserDto UserDoToUserDtoConvert(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User StudentCartDtoToUserDoConvert(StudentCardDto studentCardDto){
        User user = new User();
        BeanUtils.copyProperties(studentCardDto,user);
        return user;
    }

    public static StudentCardDto UserDoToStudentCartDtoConvert(User user) {
        StudentCardDto studentCardDto= new StudentCardDto();
        BeanUtils.copyProperties(user, studentCardDto);
        return studentCardDto;
    }

    public static StudentCardDto StudentQuerytoStudentCaedDto(StudentCardQuery studentCardQuery) {
        StudentCardDto studentCardDto = new StudentCardDto();
        BeanUtils.copyProperties(studentCardQuery, studentCardDto);
        return studentCardDto;
    }
}
