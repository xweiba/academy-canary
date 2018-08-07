package com.ptteng.academy;

import com.alibaba.fastjson.JSONObject;
import com.ptteng.academy.business.dto.HomeBannerListDto;
import com.ptteng.academy.business.dto.HomeVideoBannerDto;
import com.ptteng.academy.business.query.HomeVideoQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.service.StudyService;
import com.ptteng.academy.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class StudyServiceImplTest {

  @Resource
  private StudyService studyService;
  @Resource
  private StudyMapper studyMapper;
  @Resource
  private WechatService wechatService;

  @Test
  public void findStudyByVideoQuery() throws FindNullException {
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

  @Test
   public void findVoide() throws FindNullException {
      HomeVideoQuery homeVideoQuery = new HomeVideoQuery();

      System.out.println(JSONObject.toJSONString(studyService.findVideoBannerByQuery(homeVideoQuery)));
  }

    @Test
    public void  Wechat() throws ResourceIsNullException {
        System.out.println(wechatService.userLogin("0112dXoW17326U0EhvmW1esMoW12dXo7 "));
    }
}