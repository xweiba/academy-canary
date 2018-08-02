package com.ptteng.academy.business;


import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.persistence.beans.Study;
import com.ptteng.academy.persistence.beans.User;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author:Lin
 * Date:2018/7/25
 * Time:13:30
 */
public class Converfor {
    public static User UserDtoToUserDoConvert(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserDto UserDoToUserDtoConvert(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User StudentCartDtoToUserDoConvert(StudentCardDto studentCardDto) {
        User user = new User();
        BeanUtils.copyProperties(studentCardDto, user);
        return user;
    }

    public static StudentCardDto UserDoToStudentCartDtoConvert(User user) {
        StudentCardDto studentCardDto = new StudentCardDto();
        BeanUtils.copyProperties(user, studentCardDto);
        return studentCardDto;
    }

    public static StudentCardDto StudentQuerytoStudentCaedDto(StudentCardQuery studentCardQuery) {
        StudentCardDto studentCardDto = new StudentCardDto();
        BeanUtils.copyProperties(studentCardQuery, studentCardDto);
        return studentCardDto;
    }

    public static List<HomeArticleListDto> StudyToHomeVideoListDto(List<Study> study) {
        List<HomeArticleListDto> homeArticleListDtos = new ArrayList();
        for (Study a : study) {
            HomeArticleListDto homeArticleListDto = new HomeArticleListDto();
            BeanUtils.copyProperties(a, homeArticleListDto);
            homeArticleListDtos.add(homeArticleListDto);
            System.out.println(a);
        }
        return homeArticleListDtos;
    }
    public static HomeVideoDto StudytoHomeVideoDto(Study study) {
        HomeVideoDto homeVideoDto = new HomeVideoDto();
        BeanUtils.copyProperties(study, homeVideoDto);
        return homeVideoDto;
    }
    public static ArticleDetailsDto StudytorticleDetailsDto(Study study) {
        ArticleDetailsDto articleDetailsDto = new ArticleDetailsDto();
        BeanUtils.copyProperties(study, articleDetailsDto);
        return articleDetailsDto;
    }
}
