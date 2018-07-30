package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.ClassifyDto;
import com.ptteng.academy.business.enums.ClassifyEnum;
import com.ptteng.academy.business.enums.GradeEnum;
import com.ptteng.academy.business.enums.SubjectEnum;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: canary
 * @description: 分类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 01:35
 **/

@Api(tags = "CategoryController", description = "分类相关Api")
@RestController
public class CategoryController {
    @ApiOperation(value = "获取分类列表", notes = "根据传入分类名称返回分类对象")
    @ApiImplicitParam(name = "cName", value = "分类名称: article|grade|subject", required = true, paramType = "path", dataType = "String", defaultValue = "article")
    @GetMapping("/classify/{cName}")
    public ResponseVO getClassIfy(@PathVariable("cName") String cName) {
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
        return ResultUtil.success("获取分类列表成功", (Object) classify);
    }
}