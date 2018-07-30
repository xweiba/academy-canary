package com.ptteng.academy;

import com.alibaba.fastjson.JSONObject;
import com.ptteng.academy.business.dto.HomeBannerListDto;
import com.ptteng.academy.business.dto.HomeVideoBannerDto;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.service.StudyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class StudyServiceImplTest {

    @Resource
    StudyService studyService;
    @Resource
    private StudyMapper studyMapper;
    @Test
    public void findStudyByVideoQuery() {
        HomeVideoQuery homeVideoQuery = new HomeVideoQuery();
        System.out.println();
        for (HomeVideoBannerDto homeVideoListDto:
                studyService.findVideoBannerByQuery(homeVideoQuery).getList()) {
            // JSON会自动遍历集合属性
            System.out.println(JSONObject.toJSONString(homeVideoListDto));
        }
    }

    @Test
    public void findBanner() {
        HomeVideoQuery homeVideoQuery = new HomeVideoQuery();
        homeVideoQuery.setClassify(1);
        for (HomeBannerListDto banner :
                studyMapper.findBannerByQuery(homeVideoQuery)) {
            System.out.println(banner.toString());
        }
    }
}