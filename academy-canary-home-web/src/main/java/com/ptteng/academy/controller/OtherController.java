package com.ptteng.academy.controller;

import com.ptteng.academy.business.dto.*;
import com.ptteng.academy.business.query.PageQuery;
import com.ptteng.academy.business.query.StudentCardQuery;
import com.ptteng.academy.business.vo.ResponseRowsVO;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description:
 * author:Lin
 * Date:2018/7/28
 * Time:18:44
 */
@RestController
@RequestMapping("/student")
public class OtherController {
    @ApiOperation(value = "学生证首页", notes = "通过用户ID获取首页信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true,paramType = "path", dataType = "long")
    @GetMapping("/card/{id}")
    public ResponseVO studentCard(@PathVariable("id") Long id) {
        StudentCardDto studentCardDto = new StudentCardDto();
        studentCardDto.setNickName("用户昵称");
        studentCardDto.setHeadImgUrl("https://avatar.csdn.net/F/5/E/3_coding13.jpg");
        studentCardDto.setGrade("一年级");
        studentCardDto.setBean(24L);
        studentCardDto.setState(true);

        return ResultUtil.success("studentCard 执行了", studentCardDto);
    }

    @ApiOperation(value = "学生证资料编辑", notes = "需要传用户头像")
    @ApiImplicitParams({@ApiImplicitParam( name = "id", value = "用户id", required = true,paramType = "path", dataType = "long"),@ApiImplicitParam(name = "studentCardQuery",value = "学生实体类", required = true,dataType = "StudentCardQuery")})
    @PutMapping("/card/{id}")
    public ResponseVO updateStudentCard(@PathVariable("id") Long id, @RequestParam("headImgUrl") MultipartFile file, StudentCardQuery studentCardQuery) {
        // 文件和参数一起上传, 待调试
        return ResultUtil.success("updateStudentCard 执行了, 文件和表单数据一起提交需后期调试");
    }

    @ApiOperation(value = "获取绑定信息", notes = "传入用户ID返回绑定信息")
    @ApiImplicitParam(name = "id", value = "用户id",paramType = "path", required = true, dataType = "Long")
    @PutMapping("/card/binding/{id}")
    public ResponseVO StudentCardBinding(@PathVariable("id") Long id) {
        StudentCardBindingDto studentCardBindingDto = new StudentCardBindingDto();
        studentCardBindingDto.setPhone(15677368989L);
        studentCardBindingDto.setEmail("1789237@qq.com");
        return ResultUtil.success("StudentCardBinding 执行了", studentCardBindingDto);
    }

    @ApiOperation(value = "获取手机验证码", notes = "传入用户ID和用户电话号码发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true,paramType = "path",dataType = "Long"),
            @ApiImplicitParam(name = "phoneId", value = "手机号码", required = true, dataType = "Long")}
    )
    @PutMapping("/card/binding/phone/{id}")
    public ResponseVO StudentBindingPhone(@PathVariable("id") Long id, @RequestBody Map<String, Object> phoneId) {
        return ResultUtil.success("StudentBindingPhone 执行了");
    }

    @ApiOperation(value = "效验验证码", notes = "传入验证码效验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", paramType = "path",required = true, dataType = "Long"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "Integer")}
    )
    @PostMapping("/card/binding/phone/{id}")
    public ResponseVO StudentVerifyPhone(@PathVariable("id") Long id, @RequestBody Map<String, Object> captcha) {
        System.out.println(captcha);
        StudentBindStatusDto studentBindStatusVo = new StudentBindStatusDto();
        studentBindStatusVo.setGainBean(20L);
        studentBindStatusVo.setState(true);
        return ResultUtil.success("StudentVerifyPhone 执行了", studentBindStatusVo);
    }

    @ApiOperation(value = "获取邮箱验证码", notes = "传入用户ID和用户邮箱发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String")}
    )
    @PostMapping("/card/binding/email/{id}")
    public ResponseVO StudentBindingEmail(@PathVariable("id") Long id, @RequestBody Map<String, Object> email) {
        return ResultUtil.success("StudentBindingEmail 执行了");
    }

    @ApiOperation(value = "效验验证码", notes = "传入验证码效验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id",paramType = "path",required = true, dataType = "Long"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "Integer")}
    )
    @PutMapping("/card/binding/email/{id}")
    public ResponseVO StudentVerifyEmail(@PathVariable("id") Long id, @RequestBody Map<String, Object> captcha) {
        StudentBindStatusDto studentBindStatusVo = new StudentBindStatusDto();
        studentBindStatusVo.setGainBean(20L);
        studentBindStatusVo.setState(true);
        return ResultUtil.success("StudentVerifyMail 执行了", studentBindStatusVo);
    }

    @ApiOperation(value = "获取收藏文章信息", notes = "传入用户ID返回收藏文章列表")
    @ApiImplicitParams({@ApiImplicitParam( name = "id", value = "用户id", paramType = "path",required = true, dataType = "Long"),@ApiImplicitParam(name = "pageNum",value = "显示的页数",required = true,dataType = "int")})
    @PostMapping("/card/collect/articles/{id}")
    public ResponseRowsVO getCollectArticle(@PathVariable("id") Long id, @RequestBody Map<String,Object> pageNum) {
        List<StudentCollectDto> articles = new ArrayList<>();
        for (Long i = 1L; i <= 10; i++) {
            Date date = new Date(60 * 60);
            StudentCollectDto studentCollectDto = new StudentCollectDto();
            studentCollectDto.setTitle("这是标题");
            studentCollectDto.setArticleId(i);
            studentCollectDto.setAuthor("王五");
            studentCollectDto.setCoverPlanUrl("http://www.apachecn.org/wp-content/uploads/2017/08/java%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF%E5%9B%BE.jpeg");
            studentCollectDto.setDigest("这是摘要");
            studentCollectDto.setPraise(123L);
            studentCollectDto.setCollect(45L);
            date = new Date(date.getTime() + 30 * 60 * 1000);
            studentCollectDto.setCollectTime(date);

            articles.add(studentCollectDto);
        }

        return ResultUtil.success("getCollectArticle 已执行", articles);
    }

    @ApiOperation(value = "获取收藏视频信息", notes = "传入用户ID返回收藏视频列表")
    @ApiImplicitParams({@ApiImplicitParam( name = "id", value = "用户id",paramType = "path",required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum",value = "查询的页数",required = true,dataType = "int")})
    @PostMapping("/card/collect/videos/{id}")
    public ResponseRowsVO getCollectVideo(@PathVariable("id") Long id, @RequestBody Map<String, Object> pageNum) {
        Date date = new Date(60 * 60);
        List<Object> videos = new ArrayList<>();
        for (Long i = 1L; i <= 10; i++) {
            StudentCollectDto studentCollectVideoVo = new StudentCollectDto();
            studentCollectVideoVo.setTitle("这是标题");
            studentCollectVideoVo.setArticleId(i);
            studentCollectVideoVo.setAuthor("王五");
            studentCollectVideoVo.setCoverPlanUrl("http://www.apachecn.org/wp-content/uploads/2017/08/java%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF%E5%9B%BE.jpeg");
            studentCollectVideoVo.setDigest("这是摘要");
            studentCollectVideoVo.setPraise(123L);
            studentCollectVideoVo.setCollect(45L);
            date = new Date(date.getTime() + 30 * 60 * 1000);
            studentCollectVideoVo.setCollectTime(date);
            studentCollectVideoVo.setAuthorImgUrl("https://avatar.csdn.net/F/5/E/3_coding13.jpg");
            studentCollectVideoVo.setVideoUrl("https://rx325n.jomodns.com/r/bdcdnct.inter.71edge.com/videos/v0/20180711/40/93/e53c9c4822e188b47bdd7e7b1c68465d.f4v?key=035b0046b2b4005f677813aab385bc103&dis_k=577e9771b9f9391c14e81a65d4f8b566&dis_t=1531505915&dis_dz=CT-HuBei_WuHan&dis_st=42&src=iqiyi.com&uuid=3baee8bc-5b48ecfb-fe&rn=1531505914662&qd_tm=1531505894908&qd_tvid=1167231300&qd_vipdyn=0&qd_k=ebc0a10909edfda9cd4386fc1198fd08&cross-domain=1&qd_aid=202124401&qd_uid=&qd_stert=0&qypid=1167231300_02020031010000000000&qd_p=3baee8bc&qd_src=01010031010000000000&qd_index=1&qd_vip=0&qyid=482b307c199b23b13efb2a37b83b8f02&pv=0.1&qd_vipres=0&range=13721600-21269503");

            videos.add(studentCollectVideoVo);
        }

        return ResultUtil.success("getCollectVideo 已执行", videos);
    }

    @ApiOperation(value = "获取banner文章信息", notes = "要显示的页数")
    @ApiImplicitParam(name = "num", value = "bunner图的个数", required = true, dataType = "Integer")
    @PostMapping("/study/article/banners")
    public ResponseRowsVO getArticleBanner(@RequestBody Map<String, Object> pageNum) {
        List<Object> banners = new ArrayList<>();
        for (Long i = 1L; i <= 10; i++) {
            HomeBannerListDto homeBannerListDto = new HomeBannerListDto();
            homeBannerListDto.setTitle("banners 标题" + i);
            homeBannerListDto.setCover_plan_url("http://www.apachecn.org/wp-content/uploads/2017/08/java%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF%E5%9B%BE.jpeg");
            homeBannerListDto.setId(i);
            banners.add(homeBannerListDto);
        }
        return ResultUtil.success("getArticleBanner 已执行", banners);
    }

    @ApiOperation(value = "获取card文章列表", notes = "传入实体类")
    @ApiImplicitParam(name = "pageQuery", value = "实体类其中有每页显示的条数和要显示的页数", required = true, dataType = "pageQuery")
    @PostMapping("/study/articles")
    public ResponseRowsVO getArticles(@RequestBody PageQuery pageQuery) {
        Date date = new Date(60 * 60);
        List<StudentCollectDto> articles = new ArrayList<>();
        for (Long i = 1L; i <= 10; i++) {
            StudentCollectDto studentCollectDto = new StudentCollectDto();
            studentCollectDto.setTitle("这是标题" + i);
            studentCollectDto.setArticleId(i);
            studentCollectDto.setAuthor("王五");
            studentCollectDto.setCoverPlanUrl("http://www.apachecn.org/wp-content/uploads/2017/08/java%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF%E5%9B%BE.jpeg");
            studentCollectDto.setDigest("这是摘要");
            studentCollectDto.setPraise(123L);
            studentCollectDto.setCollect(45L);
            date = new Date(date.getTime() + 30 * 60 * 1000);
            studentCollectDto.setCollectTime(date);

            articles.add(studentCollectDto);
        }

        return ResultUtil.success("getArticles 已执行", articles);
    }

    @ApiOperation(value = "获取card文章详情信息", notes = "通过文章id获取详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章id", required = true,paramType = "path",dataType = "Long"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "long")
    })
    @PostMapping("/study/article/{id}")
    public ResponseVO getArticle(@PathVariable("id") Long id, @RequestBody Long stuId) {
        ArticleAndVideoDto articleDto = new ArticleAndVideoDto();

        articleDto.setTitle("这是标题");
        articleDto.setUpdateTime(new Date());
        articleDto.setContent("这是内容这是内容这是内容这是内容这是内容");
        articleDto.setAuthor("我是作者");
        articleDto.setPraise(23);
        articleDto.setCollect(33);
        articleDto.setPraiseStu(true);
        articleDto.setCollectStu(false);

        return ResultUtil.success("getArticle 已执行", articleDto);
    }

    @ApiOperation(value = "文章点赞操作", notes = "传入文章id和用户id-stuId点赞操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章id", required = true,paramType = "path",dataType = "stuId"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "Long")
    })
    @PutMapping("/study/article/{id}/praise")
    public ResponseVO articlePraise(@PathVariable("id") Long id, Long stuId) {
        return ResultUtil.success("articlePraise 已执行");
    }

    @ApiOperation(value = "文章收藏操作", notes = "传入文章id和用户id-stuId收藏操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章id", required = true,paramType = "path",dataType = "Long"),
            @ApiImplicitParam(name = "stuId", value = "用户Id", required = true, dataType = "Long")
    })
    @PutMapping("/study/article/{id}/collect")
    public ResponseVO articleCollect(@PathVariable("id") Long id, @RequestBody Map<String, Object> stuId) {
        return ResultUtil.success("articleCollect 已执行");
    }




}
