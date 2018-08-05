package com.ptteng.academy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.enums.ClassifyEnum;
import com.ptteng.academy.business.enums.GradeEnum;
import com.ptteng.academy.business.enums.SubjectEnum;
import com.ptteng.academy.business.query.UserQuery;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.framework.service.OSSService;
import com.ptteng.academy.persistence.beans.User;
import com.ptteng.academy.persistence.mapper.UserMapper;
import com.ptteng.academy.service.ConsumeService;
import com.ptteng.academy.persistence.mapper.AuthorMapper;
import com.ptteng.academy.persistence.beans.Author;
import com.ptteng.academy.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: canary
 * @description:
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-25 19:34
 **/
@Service
@Slf4j
public class ConsumeServiceImpl implements ConsumeService {
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private OSSService ossService;

    @Resource
    private ManageService manageService;

    @Override
    public AuthorDto insert(AuthorDto entity) throws Exception {
        /* 上传图片到OSS */
        if (!"".equals(entity.getAuthor_img()) && entity.getAuthor_img() != null) {
            entity.setAuthor_img(ossService.updateFile(entity.getAuthor_img()));
        }
        Author author = new Author();
        BeanUtils.copyProperties(entity, author);
        author.setCreate_at(new Date());
        author.setUpdate_at(new Date());
        author.setCreate_by(manageService.getOnlineAccount().getUsername());
        author.setUpdate_by(manageService.getOnlineAccount().getUsername());
        log.debug("新建author:" + author.toString());
        // 创建作者失败, 作者姓名不能为空
        authorMapper.insertSelective(author);
        BeanUtils.copyProperties(author, entity);
        return entity;
    }

    @Override
    public void insertList(List<AuthorDto> entities) throws Exception {

    }

    // 删除指定id作者
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) throws ResourceIsNullException {
        // 成功返回1, 0 id 不存在
        Boolean remove = authorMapper.deleteByPrimaryKey(primaryKey) > 0;
        log.debug("remove:" + remove);
        if (!remove) {
            throw new ResourceIsNullException();
        }
        return true;
    }

    @Override
    public boolean update(AuthorDto entity) throws Exception {
        /* 上传图片到OSS */
        if ("".equals(entity.getAuthor_img()) && entity.getAuthor_img() != null) {
            entity.setAuthor_img(ossService.updateFile(entity.getAuthor_img()));
        }
        Author author = new Author();
        BeanUtils.copyProperties(entity, author);
        author.setUpdate_at(new Date());
        return authorMapper.updateByPrimaryKey(author) > 0;
    }

    @Override
    public boolean updateByPrimaryKeySelective(AuthorDto entity) throws Exception {
        return false;
    }


    @Override
    public AuthorDto getByPrimaryKey(Long primaryKey) throws Exception {
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(authorMapper.selectByPrimaryKey(primaryKey), authorDto);
        return authorDto;
    }

    @Override
    public AuthorDto getOneByEntity(AuthorDto entity) throws Exception {
        return null;
    }

    @Override
    public List<AuthorDto> listAll() throws Exception {
        List<AuthorDto> authorDtoList = new ArrayList<AuthorDto>();
        List<Author> authorList = authorMapper.selectAll();
        for (Author au :
                authorList) {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(au, authorDto);
            authorDtoList.add(authorDto);
        }
        return authorDtoList;
    }

    @Override
    public List<AuthorDto> listByEntity(AuthorDto entity) throws Exception {
        return null;
    }

    // 根据用户名返回id
    @Override
    public Long findAuthorByName(String name) throws Exception {
        Author author = new Author();
        author.setAuthor_name(name);
        author = authorMapper.selectOne(author);
        if (author ==null){
            throw new ResourceIsNullException("该老师不存在");
        }
        return author.getId();
    }

    // 根据id返回用户名
    @Override
    public String findAuthorById(Long id) throws Exception {
        Author author = new Author();
        author.setId(id);
        author = authorMapper.selectOne(author);
        return author.getAuthor_name();
    }

    @Override
    public PageInfo<UserBackDto> findUser(UserQuery userQuery) throws Exception {
        log.info("查询用户列表传入参数：" + userQuery);
        PageHelper.startPage(userQuery.getPageNum(), userQuery.getPageSize());
        List<UserBackDto> userBackDtoList = userMapper.findUser(userQuery);
        PageInfo<UserBackDto> pageInfo = new PageInfo<UserBackDto>(userBackDtoList);
        return pageInfo;
    }

    // 获取枚举列表
    @Override
    public List<Object> findListByName(String cName) throws Exception {
        List<Object> classify = new ArrayList<Object>();
        if ("article".equals(cName)) {
            for (ClassifyEnum article :
                    ClassifyEnum.values()) {
                ClassifyDto classifyDto = new ClassifyDto();
                classifyDto.setId(article.getCode());
                classifyDto.setName(article.getName());
                classify.add(classifyDto);
            }
        }
        if ("grade".equals(cName)) {
            for (GradeEnum gradeEnum :
                    GradeEnum.values()) {
                ClassifyDto classifyDto = new ClassifyDto();
                classifyDto.setId(gradeEnum.getCode());
                classifyDto.setName(gradeEnum.getGrade());
                classify.add(classifyDto);
            }
        }
        if ("subject".equals(cName)) {
            for (SubjectEnum subjectEnum :
                    SubjectEnum.values()) {
                ClassifyDto classifyDto = new ClassifyDto();
                classifyDto.setId(subjectEnum.getCode());
                classifyDto.setName(subjectEnum.getSubject());
                classify.add(classifyDto);
            }
        }
        return classify;
    }

    @Override
    public Integer updateUserStatus(Long id) throws Exception {
        Integer integer = userMapper.updateUserStatus(id, new Date(),manageService.getOnlineAccount().getUsername());
        // 0 为无变化. 说明id不存在
        if (integer == 0){
            throw new ResourceIsNullException();
        }
        return integer;
    }

    @Override
    public UserDto findUserById(Long id) throws Exception {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new FindNullException();
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
