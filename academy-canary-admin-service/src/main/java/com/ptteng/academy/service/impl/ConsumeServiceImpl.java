package com.ptteng.academy.service.impl;

import com.ptteng.academy.business.dto.AuthorDto;
import com.ptteng.academy.framework.service.OSSService;
import com.ptteng.academy.service.ConsumeService;
import com.ptteng.academy.persistence.mapper.AuthorMapper;
import com.ptteng.academy.persistence.beans.Author;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
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
public class ConsumeServiceImpl implements ConsumeService {
    @Autowired
    private AuthorMapper authorMapper;

    @Resource
    private OSSService ossService;

    @Override
    public AuthorDto insert(AuthorDto entity) throws Exception{
        /* 上传图片到OSS */
        if (!"".equals(entity.getAuthor_img()) && entity.getAuthor_img()!=null) {
            entity.setAuthor_img(ossService.updateFile(entity.getAuthor_img()));
        }
        Author author = new Author();
        BeanUtils.copyProperties(entity,author);
        author.setCreate_at(new Date());
        author.setUpdate_at(new Date());
        author.setCreate_by("admin");
        author.setUpdate_by("admin");
        authorMapper.insert(author);
        BeanUtils.copyProperties(author,entity);
        return entity;
    }

    @Override
    public void insertList(List<AuthorDto> entities) {

    }
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        // 成功返回1, 失败返回0
        return authorMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    @Override
    public boolean update(AuthorDto entity) throws FileNotFoundException {
        /* 上传图片到OSS */
        if ("".equals(entity.getAuthor_img()) && entity.getAuthor_img()!=null) {
            entity.setAuthor_img(ossService.updateFile(entity.getAuthor_img()));
        }
        Author author = new Author();
        BeanUtils.copyProperties(entity, author);
        author.setUpdate_at(new Date());
        return authorMapper.updateByPrimaryKey(author) > 0;
    }

    @Override
    public boolean updateByPrimaryKeySelective(AuthorDto entity) {
        return false;
    }

    @Override
    public AuthorDto getByPrimaryKey(Long primaryKey) {
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(authorMapper.selectByPrimaryKey(primaryKey), authorDto);
        return authorDto;
    }

    @Override
    public AuthorDto getOneByEntity(AuthorDto entity) {
        return null;
    }

    @Override
    public List<AuthorDto> listAll() {
        List<AuthorDto> authorDtoList = new ArrayList<AuthorDto>();
        List<Author> authorList = authorMapper.selectAll();
        for (Author au :
                authorList) {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(au,authorDto);
            authorDtoList.add(authorDto);
        }
        return authorDtoList;
    }

    @Override
    public List<AuthorDto> listByEntity(AuthorDto entity) {
        return null;
    }

    // 根据用户名返回id
    @Override
    public Long findAuthorByName(String name) {
        Author author = new Author();
        author.setAuthor_name(name);
        author = authorMapper.selectOne(author);
        return author.getId();
    }
    // 根据id返回用户名
    @Override
    public String findAuthorById(Long id) {
        Author author = new Author();
        author.setId(id);
        author = authorMapper.selectOne(author);
        return author.getAuthor_name();
    }
}
