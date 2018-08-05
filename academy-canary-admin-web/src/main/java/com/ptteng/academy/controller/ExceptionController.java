package com.ptteng.academy.controller;

import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.framework.exception.FindNullException;
import com.ptteng.academy.framework.exception.ResourceIsNullException;
import com.ptteng.academy.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.type.TypeException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: canary
 * @description: 异常mapper
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-08-05 14:22
 **/

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    // 设置本项目包路径
    private String classPath = "com.ptteng.academy";

    // BeanUtils 转换异常,  数据源为空, 注意数据为空并不代表错误
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseVO BeanUtilsErrorHandler(IllegalArgumentException e) throws Exception {
        log.debug("\n发生BeanUtils转换异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.success(ResponseCodeEnum.BEAN_UTIL_ERROR);
    }

    // 类型转换失败
    @ExceptionHandler(value = ClassCastException.class)
    public ResponseVO ClassCastExceptionErrorHandler(ClassCastException e) throws Exception {
        log.debug("\n发生类型转换异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.CAST_TO_ERROR);
    }

    /* 数据库 */
    // 数据库设置参数异常
    @ExceptionHandler(value = TypeException.class)
    public ResponseVO TypeExceptionErrorHandler(TypeException e) throws Exception {
        log.debug("\n发生数据库无法设置参数异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAMETER_ERROR);
    }

    // 数据库约束异常
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseVO dataIntegrityViolationExceptionErrorHandler(DataIntegrityViolationException e) throws Exception {
        log.debug("\n发生数据库唯一约束异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAMETER_ERROR);
    }

    // 数据库资源不在
    @ExceptionHandler(value = ResourceIsNullException.class)
    public ResponseVO RemoveResourceExceptionErrorHandler(ResourceIsNullException e) throws Exception{
        log.debug("\n资源空异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        // 判断是否使用了自定义消息
        if (e.getMessage()!=null) {
            return ResultUtil.error(ResponseCodeEnum.SQL_RESOURCE_ERROR.getCode(), e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_RESOURCE_ERROR);
    }

    // 未查询到数据
    @ExceptionHandler(value = FindNullException.class)
    public ResponseVO findNullExceptionHadnler(FindNullException e) throws Exception {
        log.debug("\n资源空异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        // 判断是否使用了自定义消息
        if (e.getMessage()!=null) {
            return ResultUtil.error(ResponseCodeEnum.FIND_NULL_SUCCESS.getCode(), e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.FIND_NULL_SUCCESS);
    }

    // 查询参数错误
    @ExceptionHandler(value = MyBatisSystemException.class)
    public ResponseVO findParamExceptionErrorHandler(MyBatisSystemException e) throws Exception{
        log.debug("\n发生sql参数错误异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAM_ERROR);
    }


    /* 文件异常处理 */
    // 文件转换为输入流异常
    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseVO fileNotFoundExceptionErrorHandler(FileNotFoundException e) throws Exception {
        log.debug("\n发生文件转换输入流失败异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.FILE_NOT_FOUND_ERROR);
    }

    // 空文件异常
    @ExceptionHandler(value = IOException.class)
    public ResponseVO iOExceptionErrorHandler(IOException e) throws Exception {
        log.debug("\n发生空文件异常,可能是文件保存路径不存在\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.IO_EXCEPTION_ERROR);
    }

    // 空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseVO nullPointerExceptionError(NullPointerException e) {
        log.debug("\n发生空指针异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.NULL_POINTER_ERROR);
    }
    // 未处理异常
    @ExceptionHandler(value = Exception.class)
    public ResponseVO exceptionError(Exception e) {
        log.debug("\n发生未处理异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(ExceptionUtils.getStackTrace(e).indexOf(classPath), ExceptionUtils.getStackTrace(e).indexOf("org.springframework.cglib.proxy.MethodProxy.invoke")),
                ExceptionUtils.getStackTrace(e));
        return ResultUtil.error(ResponseCodeEnum.ERROR);
    }

}