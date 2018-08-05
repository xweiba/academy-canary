package com.ptteng.academy;

import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.business.dto.UserDto;
import com.ptteng.academy.business.query.StudyQuery;
import com.ptteng.academy.service.ConsumeService;
import com.ptteng.academy.service.StudyService;
import com.ptteng.academy.util.ResultUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.FileNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CanaryAdminLaunch.class)
public class CategoryControllerTest {

    @Resource
    private ConsumeService consumeService;
    @Resource
    private StudyService studyService;

    @Test
    public void listAll() {
        List<AuthorDto> authorList = consumeService.listAll();
        for (AuthorDto au :
                authorList) {
            System.out.println(au.toString());
        }
    }
    @Test
    public void findById() throws Exception {
        System.out.println(consumeService.getByPrimaryKey(1L));
    }

    @Test
    public void insert() throws Exception {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthor_img("1232321");
        authorDto.setAuthor_name("21321");
        System.out.println(consumeService.insert(authorDto).toString());
    }
    @Test
    public void delete() {
        Assert.assertEquals(true, consumeService.removeByPrimaryKey(4L));
    }
    @Test
    public void update() throws Exception {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(7L);
        authorDto.setAuthor_img("7777");
        authorDto.setAuthor_name("777");
        Assert.assertEquals(true, consumeService.update(authorDto));
    }

    @Test
    public void test() {
        UserDto authorDto = new UserDto();
        authorDto.setId(1L);
        // JSONObject.toJSON(authorDto) 会将为null的属性全部移除
        System.out.println(ResultUtil.success("getAuthor 调用成功", authorDto));
    }

    @Test
    public void findTest() throws Exception {
        StudyQuery studyQuery = new StudyQuery();
        studyQuery.setTitle("标题");
        System.out.println(studyService.findPageBreakByCondition(studyQuery).getList());

    }
}