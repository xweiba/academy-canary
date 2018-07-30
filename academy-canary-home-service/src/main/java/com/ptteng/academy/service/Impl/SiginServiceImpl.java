package com.ptteng.academy.service.Impl;

import com.ptteng.academy.business.dto.SigningDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.persistence.beans.Signin;
import com.ptteng.academy.persistence.mapper.LoginMapper;
import com.ptteng.academy.service.SiginService;
import com.ptteng.academy.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //累计签到多少天
    private static int coutinus = 0;
    private final Logger logger = LoggerFactory.getLogger(SiginServiceImpl.class);

    //查询签到信息
    @Override
    public UserDto selectSiginById(Long id){
        UserDto userDto =  loginMapper.findUserById(id);
        //将数据库签到历史long转成字符串
        final int size = 31;
        char[] chs = new char[size];
        for (int i = 0; i < size; i++) {
            chs[size - 1 - i] = (char) (((20701L >> i) & 1) + '0');
        }
        userDto.setSigninHistory(new String(chs));
        return userDto;
    }

    //点击签到
    @Override
    public ResponseVO sigin(Long id){
        //获取当前时间
        Date date = new Date();
        logger.info("签到传入的用户id: "+id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringNowTime = format.format(date);
        //获取签到资料
        Signin userDto = loginMapper.findSiginById(id);
        logger.info("签到信息: "+ userDto);
        //返回值的对象
        SigningDto signingDto = new SigningDto();

        if (userDto != null) {
            Long loginTime = userDto.getLastSigninTime();
           //判断用户是否第一次签到
            if (loginTime == null){
                userDto.setTopContinuouSignin(0);
                userDto.setLastSigninTime(0L);
                userDto.setSigninCount(0);
                userDto.setSigninHistory(0L);
            }
            Date dateLoginTime = new Date(loginTime);
            String stringLoginTime = format.format(dateLoginTime);
            //判断今天是否签到过
            if (java.sql.Date.valueOf(stringNowTime).after(java.sql.Date.valueOf(stringLoginTime))) {
                //签到
                userDto.setLastSigninTime(new Date().getTime());
                //更新签到历史
                Long historySigin = userDto.getSigninHistory();
                long diff = java.sql.Date.valueOf(stringNowTime).getTime() - java.sql.Date.valueOf(stringLoginTime).getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                logger.info("登陆间隔时间： " +days);
                //更新没登陆的记录
                if (days>1){
                    for (int p = 1;p < days;p++)
                        historySigin = (historySigin << 1);
                }
                //更新当前签到记录
                historySigin = (historySigin << 1) | 1;
                userDto.setSigninHistory(historySigin);

                final Long fHistory = historySigin;
                String stringHistory = Long.toBinaryString(fHistory);
                logger.info("签到历史：" + stringHistory);
                //遍历签到历史返回连续签到次数
                for(int i = stringHistory.length() - 1; i >= 0; i--) {
                    if (stringHistory.charAt(i) == '1'){
                        coutinus += 1;
                    }else {
                        break;
                    }
                }
                //判断连续签到次数奖励逆袭豆
                if (coutinus >= 5) {
                signingDto.setGainBean(5);
                }else {
                    signingDto.setGainBean(coutinus);
                }

                final int size = 31;
                char[] chs = new char[size];
                for (int i = 0; i < size; i++) {
                    chs[size - 1 - i] = (char) (((fHistory >> i) & 1) + '0');
                }
                //签到总次数+1
                userDto.setSigninCount(userDto.getSigninCount() + 1);

                //连续签到天数 > 最大连续签到天数
                if (coutinus > userDto.getTopContinuouSignin()) {
                    userDto.setTopContinuouSignin(coutinus);
                }
                //更新用户签到信息
                loginMapper.updateByPrimaryKey(userDto);
                signingDto.setState(true);
                return ResultUtil.success("签到成功", signingDto);
            } else {
                return ResultUtil.error("今日已签到请勿重复签到");
            }
        }else {
            //错误501
            return ResultUtil.error(ResponseCodeEnum.USER_UNEXIST);
        }
    }

}
