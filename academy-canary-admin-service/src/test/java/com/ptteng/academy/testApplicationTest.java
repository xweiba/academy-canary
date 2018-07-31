package com.ptteng.academy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.StudyDto;
import com.ptteng.academy.business.query.*;
import com.ptteng.academy.framework.config.TaskConfig;
import com.ptteng.academy.framework.config.QiNiuYun;
import com.ptteng.academy.persistence.mapper.ModuleMapper;
import com.ptteng.academy.persistence.mapper.StudyMapper;
import com.ptteng.academy.service.ManageService;
import com.ptteng.academy.service.StudyService;
import com.ptteng.academy.util.RandNumUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = testApplication.class)
public class testApplicationTest {
    @Resource
    StudyService studyService;

    @Resource
    StudyMapper studyMapper;

    @Resource
    QiNiuYun qiNiuYun;

    @Resource
    ModuleMapper moduleMapper;

    @Test
    public void instert() throws Exception {
        StudyDto studyDto = new StudyDto();
        for (int i = 0; i < 1; i++) {
            studyDto.setContent("正文" + RandNumUtil.getRandLength(20) + "正文正文正文正文正文正文正文正文");
            studyDto.setAuthor(RandNumUtil.getRandRange(1, 7));
            studyDto.setClassify(RandNumUtil.getRandRange(1, 2));
            studyDto.setCollect(Long.valueOf(RandNumUtil.getRandRange(20, 1000)));
            studyDto.setIntroduce("简介简介简介简介简介简介" + RandNumUtil.getRandLength(5));
            studyDto.setCover_plan_url("http://93.179.100.194:8080/cover_plan.jpg");
            studyDto.setVideo_url("http://93.179.100.194:8080/test_video.mp4");
            studyDto.setGrade(RandNumUtil.getRandRange(1, 6));
            studyDto.setSubject(RandNumUtil.getRandRange(1, 6));
            studyDto.setTitle("这是标题" + RandNumUtil.getRandLength(5));
            studyDto.setVideo_time(Long.valueOf(RandNumUtil.getRandRange(200, 980)));
            studyDto.setStudy_type(RandNumUtil.getRandRange(1, 2));
            studyDto.setPraise(Long.valueOf(RandNumUtil.getRandRange(30, 500)));
            studyDto.setStatus(RandNumUtil.getRandRange(1, 10) > 5);
            System.out.println("studyService.insert(study)" + studyService.insert(studyDto).toString());
            studyDto.setId(null);
        }
    }

    /* 视频查询 */
    @Test
    public void findVideo() throws Exception {
        VideoQuery videoQuery = new VideoQuery();
        // videoQuery.setPageNum(2);
        // videoQuery.setPageSize(10);
/*        videoQuery.setTitle("0");
        Integer[] aa = {1, 300};
        videoQuery.setStatus(true);
        videoQuery.setPraise(aa);*/
        StudyQuery studyQuery = new StudyQuery();
        videoQuery.setTitle("这是标");
        BeanUtils.copyProperties(videoQuery, studyQuery);
        PageInfo<?> videoDtoPageInfo = studyService.findPageBreakByCondition(studyQuery);
        System.out.println(videoDtoPageInfo.getTotal());
        System.out.println(videoDtoPageInfo.getSize());
    }

    /* 文章查询 */
    @Test
    public void articlQueryTest() throws Exception {
        ArticleQuery articleQuery = new ArticleQuery();
        StudyQuery studyQuery = new StudyQuery();
        BeanUtils.copyProperties(articleQuery, studyQuery);
        PageInfo<?> videoDtoPageInfo = studyService.findPageBreakByCondition(studyQuery);
    }

    @Test
    public void deleteFile() {
        qiNiuYun.deleteFile("QQ截图20180716093633.png");
    }

    @Resource
    TaskConfig taskConfig;
    @Test
    public void contextLoads() {
        taskConfig.setCron("0/10 * * * * ?");
    }

    @Test
    public void delete() {
        File file = new File("ff0268e941e87e4d0561be51d4b5d100.png");
        System.out.println("file.getPath():" + file.getPath());
        
        if(file.delete()){
            System.out.println(file.getName() + " 文件已被删除！");
        }else{
            System.out.println("文件删除失败！");
        }
    }

    @Test
    public void findVide() {
        System.out.println(studyMapper.findStudyByQuery(199L, 2L));
    }

    @Resource
    ManageService manageService;

    @Test
    public void findMould() {
        ModuleQuery moduleQuery = new ModuleQuery();
        moduleQuery.setModuleName("用户");
        System.out.println(JSONObject.toJSONString(moduleMapper.findModuleByName(moduleQuery)));
        System.out.println(JSONObject.toJSONString(manageService.findModuleByQuery(moduleQuery)));
    }

    @Test
    public void findMouldById() {
        System.out.println(JSONObject.toJSONString(manageService.findModuleById(1L)));
    }

    @Test
    public void findRole() {
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setId(1L);
        System.out.println(JSONObject.toJSONString(manageService.findRoleByQuery(roleQuery)));
    }

    @Test
    public void findAccount() {
        AccountQuery accountQuery = new AccountQuery();
        accountQuery.setUsername("admin");
        System.out.println(JSONObject.toJSONString(manageService.findAccountByQuery(accountQuery)));
    }
    @Test
    public void findAccountById() {
        System.out.println(JSONObject.toJSONString(manageService.findAccountById(1L)));
    }
}