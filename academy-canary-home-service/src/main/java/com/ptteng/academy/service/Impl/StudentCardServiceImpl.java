package com.ptteng.academy.service.Impl;

import com.ptteng.academy.business.Converfor;
import com.ptteng.academy.business.dto.StudentCardDto;
import com.ptteng.academy.business.dto.WeixinAccessToken;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.StudentCardService;
import com.ptteng.academy.util.WechatDoloadImgUtil;
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

    //查询学生证资料
    @Override
    public StudentCardDto selectAll(Long id) {
        StudentCardDto studentCardDto = new StudentCardDto();
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            return Converfor.UserDoToStudentCartDtoConvert(user);
        } else {
            return studentCardDto;
        }
    }

    //修改学生证资料
    @Override
    public boolean updateStudentCard(StudentCardQuery studentCardQuery) {
        StudentCardDto studentCardDto = new StudentCardDto();
        String media_id = studentCardQuery.getMedia_id();
        studentCardDto = Converfor.StudentQuerytoStudentCaedDto(studentCardQuery);
        if (media_id != null) {
            try {
                String imageUrl = WechatDoloadImgUtil.downloadMedia(WeixinAccessToken.getAccessToken(), media_id);
                studentCardDto.setHeadImgUrl(imageUrl);
            } catch (Exception e) {
                System.out.println("图片上传错误"+e);
            }
        }
        User user = Converfor.StudentCartDtoToUserDoConvert(studentCardDto);
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }
}
