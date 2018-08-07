package com.ptteng.academy.framework.exception;

import com.aliyuncs.exceptions.ClientException;
import com.ptteng.academy.business.enums.ResponseCodeEnum;
import com.ptteng.academy.business.vo.ResponseVO;
import com.ptteng.academy.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.type.TypeException;
import org.apache.shiro.authc.AuthenticationException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.RedisConnectionFailureException;
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
public class ExceptionMapper {
    // 设置本项目包路径 异常详细信息开始关键字
    private String classPath = "com.ptteng.academy";
    // 异常详细信息结束位置
    private Integer endIndex = 500;
    private String exceptionTag = "提醒:";

    // BeanUtils 转换异常,  数据源为空, 注意数据为空并不代表错误
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseVO BeanUtilsErrorHandler(IllegalArgumentException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生转换异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.success(ResponseCodeEnum.BEAN_UTIL_ERROR);
    }

    // 类型转换失败
    @ExceptionHandler(value = ClassCastException.class)
    public ResponseVO ClassCastExceptionErrorHandler(ClassCastException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生类型转换异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.CAST_TO_ERROR);
    }

    /* 数据库 */
    // 数据库设置参数异常
    @ExceptionHandler(value = TypeException.class)
    public ResponseVO TypeExceptionErrorHandler(TypeException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生数据库设置参数异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAMETER_ERROR);
    }

    // 数据库参数转换异常
    @ExceptionHandler(value = StringIndexOutOfBoundsException.class)
    public ResponseVO StringIndexOutOfBoundsExceptionErrorHandler(StringIndexOutOfBoundsException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生数据库参数转换异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAMETER_ERROR);
    }

    // 数据库约束异常
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseVO dataIntegrityViolationExceptionErrorHandler(DataIntegrityViolationException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生数据库约束异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAMETER_ERROR);
    }

    // 数据库唯一约束异常
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseVO duplicateKeyExceptionErrorHandler(DuplicateKeyException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生主键约束异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_KEY_PARAMETER_ERROR);
    }

    // 资源不存在
    @ExceptionHandler(value = ResourceIsNullException.class)
    public ResponseVO resourceExceptionErrorHandler(ResourceIsNullException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生资源不存在异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        // 判断是否使用了自定义消息
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_RESOURCE_ERROR);
    }

    // 未查询到数据
    @ExceptionHandler(value = FindNullException.class)
    public ResponseVO findNullExceptionHadnler(FindNullException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生查询到空数据异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        // 判断是否使用了自定义消息
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(200, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.FIND_NULL_SUCCESS);
    }

    // 查询参数错误
    @ExceptionHandler(value = MyBatisSystemException.class)
    public ResponseVO findParamExceptionErrorHandler(MyBatisSystemException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生查询参数错误异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SQL_PARAM_ERROR);
    }

    /* 登陆异常处理 */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseVO authenticationExceptionErrorHandler(AuthenticationException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生认证失败异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.LOGIN_ERROR);
    }


    /* 文件异常处理 */
    // 文件转换为输入流异常
    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseVO fileNotFoundExceptionErrorHandler(FileNotFoundException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生文件转换为输入流异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.FILE_NOT_FOUND_ERROR);
    }

    // 空文件异常
    @ExceptionHandler(value = IOException.class)
    public ResponseVO iOExceptionErrorHandler(IOException e) throws Exception {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生空文件异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.IO_EXCEPTION_ERROR);
    }

    // redis 初始化异常
    @ExceptionHandler(value = RedisConnectionFailureException.class)
    public ResponseVO redisConnectionFailureExceptionError (RedisConnectionFailureException e) {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生redis初始化异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage()!=null && e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.REDIS_GET_ERROR);
    }
    // 阿里云短信异常
    @ExceptionHandler(value = ClientException.class)
    public ResponseVO clientExceptionError(ClientException e) {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生阿里云短信异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage()!=null && e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.SMS_SEND_ERROR);
    }

    // 空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseVO nullPointerExceptionError(NullPointerException e) {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生空指针异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage()!=null && e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.NULL_POINTER_ERROR);
    }

    // 未处理异常
    @ExceptionHandler(value = Exception.class)
    public ResponseVO exceptionError(Exception e) {
        Integer indexStart = ExceptionUtils.getStackTrace(e).indexOf(classPath);
        Integer indexEnd = indexStart + endIndex;
        log.debug("\n发生未处理异常\n异常类型是:[{}]\n异常消息是:[{}]\n异常详细位置是:\n[{}]\n异常详细信息是:\n[{}]\n",
                e.getClass().getName(),
                ExceptionUtils.getMessage(e),
                // 获取异常发生位置
                ExceptionUtils.getStackTrace(e).substring(indexStart, indexEnd),
                ExceptionUtils.getStackTrace(e));
        if (e.getMessage()!=null && e.getMessage().contains(exceptionTag)) {
            return ResultUtil.error(500, e.getMessage());
        }
        return ResultUtil.error(ResponseCodeEnum.ERROR);
    }
}