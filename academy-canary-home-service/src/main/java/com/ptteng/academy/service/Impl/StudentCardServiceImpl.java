package com.ptteng.academy.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.Converfor;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.StudentCardService;
import com.ptteng.academy.util.WechatDoloadImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:学生证
 * author:Lin
 * Date:2018/7/29
 * Time:15:20
 */
@Slf4j
@Service
public class StudentCardServiceImpl implements StudentCardService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StudyMapper studyMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //查询学生证资料
    @Override
    public StudentCardDto selectAll(Long id) throws ResourceIsNullException {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new ResourceIsNullException("提醒: 该用户不存在~");
        }
        StudentCardDto studentCardDto = new StudentCardDto();
        BeanUtils.copyProperties(user, studentCardDto);
        return studentCardDto;
    }

    //修改学生证资料
    @Override
    public boolean updateStudentCard(StudentCardQuery studentCardQuery) throws ResourceIsNullException {
        String media_id = studentCardQuery.getMedia_id();
        StudentCardDto studentCardDto = new StudentCardDto();
        studentCardDto = Converfor.StudentQuerytoStudentCaedDto(studentCardQuery);
        if (media_id != null) {
            try {
                String imageUrl = WechatDoloadImgUtil.downloadMedia(WeixinAccessToken.getAccessToken(), media_id);
                studentCardDto.setHeadImgUrl(imageUrl);
            } catch (Exception e) {
                log.debug("图片上传失败");
                e.printStackTrace();
            }
        }
        User user = Converfor.StudentCartDtoToUserDoConvert(studentCardDto);
        Integer updateStatus = userMapper.updateByPrimaryKeySelective(user);
        if (updateStatus == 0) {
            throw new ResourceIsNullException("提醒: 用户资料未变更或用户不存在~");
        }
        return true;
    }

    //查询我的收藏文章
    @Override
    public PageInfo findCollectArticle(Long id, Integer pageNum) throws FindNullException {
        PageHelper.startPage(pageNum, 10);
        List<StudentCollectArticleDto> studentCollectArticleDtos = studyMapper.findCollectArticle(id);
        if (studentCollectArticleDtos.isEmpty()) {
            throw new FindNullException("提醒: 未查询到该用户的收藏文章~");
        }
        PageInfo<StudentCollectArticleDto> bean = new PageInfo<>(studentCollectArticleDtos);
        bean.setList(studentCollectArticleDtos);
        return bean;
    }

    //查询我的收藏视频
    @Override
    public PageInfo<HomeVideoDto> findCollectVideo(Long id, Integer pageNum) throws FindNullException {
        PageHelper.startPage(pageNum, 10);
        List<HomeVideoDto> studyMapperCollectVideo = studyMapper.findCollectVideo(id);
        if (studyMapperCollectVideo.isEmpty()) {
            throw new FindNullException("提醒: 未查询到该用户的收藏视频~");
        }
        PageInfo<HomeVideoDto> bean = new PageInfo<>(studyMapperCollectVideo);
        bean.setList(studyMapperCollectVideo);
        return bean;
    }

    //效验邮箱验证码
    @Override
    public SigningDto verifyCode(Long id, String code) throws ResourceIsNullException {
        String email = stringRedisTemplate.opsForValue().get(code);
        if (email == null || email.equals("")) {
            throw new ResourceIsNullException("提醒: 验证失败! 验证码不存在或已失效!");
        }
        // 成功取出后删除缓存
        stringRedisTemplate.delete(code);

        User user3 = new User();
        user3.setId(id);
        User user1 = userMapper.selectOne(user3);
        SigningDto signingDto = new SigningDto();
        if (user1.getPhone() != null && !user1.getBinding()) {
            user1.setBinding(true);
            user1.setBean(user1.getBean() + 20);
            signingDto.setGainBean(20);
            userMapper.updateByPrimaryKeySelective(user1);
        }
        signingDto.setState(true);
        log.info("验证成功后的邮箱：" + email);
        User user = new User();
        user.setEmail(email);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
        signingDto.setState(true);
        return signingDto;
    }

    //效验手机验证码
    @Override
    public SigningDto verifyCodePhone(Long id, String code) throws ResourceIsNullException {
        String phone = stringRedisTemplate.opsForValue().get(code);
        if (phone == null || phone.equals("")) {
            throw new ResourceIsNullException("提醒: 验证失败! 验证码不存在或已失效!");
        }
        // 成功取出后删除缓存
        stringRedisTemplate.delete(code);

        SigningDto signingDto = new SigningDto();
        User user3 = new User();
        user3.setId(id);
        User user1 = userMapper.selectOne(user3);
        if (user1.getEmail() != null && !user1.getBinding()) {
            user1.setBinding(true);
            user1.setBean(user1.getBean() + 20);
            signingDto.setGainBean(20);
            userMapper.updateByPrimaryKeySelective(user1);
        }
        Long lPhone = Long.parseLong(phone);
        log.info("验证成功后的电话：" + phone);
        User user = new User();
        user.setPhone(lPhone);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
        signingDto.setState(true);
        return signingDto;
    }
}
