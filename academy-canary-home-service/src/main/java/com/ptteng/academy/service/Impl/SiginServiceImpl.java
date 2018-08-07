package com.ptteng.academy.service.Impl;

import com.ptteng.academy.business.dto.SigningDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.beans.Signin;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.LoginMapper;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.SiginService;
import com.ptteng.academy.util.ResultUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * description:
 * author:Lin
 * Date:2018/7/26
 * Time:17:54
 */
@Service
public class SiginServiceImpl implements SiginService {
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    UserMapper userMapper;

    //累计签到多少天
    private static int coutinus = 0;
    private final Logger logger = LoggerFactory.getLogger(SiginServiceImpl.class);

    //查询签到信息
    @Override
    public UserDto selectSiginById(Long id) throws ResourceIsNullException {
        UserDto userDto = loginMapper.findUserById(id);
        if (userDto == null) {
            throw new ResourceIsNullException("提醒: 该用户不存在~");
        }
        String siginHistory1 = userDto.getSigninHistory();
        long siginHistory = Long.parseLong(siginHistory1);
        logger.info("签到历史：" + siginHistory);
        //将数据库签到历史long转成字符串
        final int size = 31;
        char[] chs = new char[size];
        for (int i = 0; i < size; i++) {
            chs[size - 1 - i] = (char) (((siginHistory >> i) & 1) + '0');
        }
        userDto.setSigninHistory(new String(chs));
        return userDto;
    }

    //点击签到
    @Override
    public SigningDto sigin(Long id) throws ResourceIsNullException {
        logger.info("签到传入的用户id: " + id);
        //获取签到资料, 判断用户是否存在
        User userStatus = userMapper.findUserById(id);
        if (userStatus == null) {
            throw new ResourceIsNullException("提醒: 该用户不存在");
        }
        //获取签到资料, 判断用户是否存在
        Signin sigin = loginMapper.findSiginById(id);
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringNowTime = format.format(date);
        logger.info("签到信息: " + sigin);
        //返回值的对象
        SigningDto signingDto = new SigningDto();
        if (sigin != null) {
            Long loginTime = sigin.getLastSigninTime();
            Date dateLoginTime = new Date(loginTime);
            String stringLoginTime = format.format(dateLoginTime);
            //判断今天是否签到过
            if (java.sql.Date.valueOf(stringNowTime).after(java.sql.Date.valueOf(stringLoginTime))) {
                //签到
                sigin.setLastSigninTime(new Date().getTime());
                //更新签到历史
                Long historySigin = sigin.getSigninHistory();
                long diff = java.sql.Date.valueOf(stringNowTime).getTime() - java.sql.Date.valueOf(stringLoginTime).getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                logger.info("登陆间隔时间： " + days);
                //更新没登陆的记录
                if (days > 1) {
                    for (int p = 1; p < days; p++)
                        historySigin = (historySigin << 1);
                }
                //更新当前签到记录
                historySigin = (historySigin << 1) | 1;
                sigin.setSigninHistory(historySigin);
                sigin.setUpdate_at(new Timestamp(System.currentTimeMillis()));
                sigin.setUpdate_by(id.toString());

                final Long fHistory = historySigin;
                String stringHistory = Long.toBinaryString(fHistory);
                logger.info("签到历史：" + stringHistory);
                //遍历签到历史返回连续签到次数
                for (int i = stringHistory.length() - 1; i >= 0; i--) {
                    if (stringHistory.charAt(i) == '1') {
                        coutinus += 1;
                    } else {
                        break;
                    }
                }
                User user1 = new User();
                user1.setId(id);
                User user = userMapper.selectOne(user1);
                logger.info("用户信息：" + user);
                if (user.getBean() != null) {
                    //判断连续签到次数奖励逆袭豆
                    if (coutinus >= 5) {
                        signingDto.setGainBean(5);
                        user.setBean(user.getBean() + 5);
                    } else {
                        signingDto.setGainBean(coutinus);
                        user.setBean(user.getBean() + coutinus);
                    }
                } else                     //判断连续签到次数奖励逆袭豆
                    if (coutinus >= 5) {
                        signingDto.setGainBean(5);
                        user.setBean(user.getBean() + 5);
                    } else {
                        signingDto.setGainBean(coutinus);
                        user.setBean(coutinus);
                    }
                //更新逆袭豆
                userMapper.updateByPrimaryKeySelective(user);
                final int size = 31;
                char[] chs = new char[size];
                for (int i = 0; i < size; i++) {
                    chs[size - 1 - i] = (char) (((fHistory >> i) & 1) + '0');
                }
                //签到总次数+1
                sigin.setSigninCount(sigin.getSigninCount() + 1);

                //连续签到天数 > 最大连续签到天数
                if (coutinus > sigin.getTopContinuouSignin()) {
                    sigin.setTopContinuouSignin(coutinus);
                }
                //更新用户签到信息
                loginMapper.updateByPrimaryKey(sigin);
                signingDto.setState(true);
                return signingDto;
            } else {
                throw new ResourceIsNullException("提醒: 今日已签到请勿重复签到");
            }
        } else {
            Signin signin = new Signin();
            signin.setId(id);
            signin.setLastSigninTime(new Date().getTime());
            signin.setSigninCount(1);
            signin.setTopContinuouSignin(1);
            signin.setSigninHistory(1L);
            signin.setCreate_at(new Timestamp(System.currentTimeMillis()));
            signin.setCreate_by("admin");
            loginMapper.insert(signin);
            signingDto.setState(true);
            signingDto.setGainBean(1);
            //错误501
            return signingDto;
        }
    }

}
