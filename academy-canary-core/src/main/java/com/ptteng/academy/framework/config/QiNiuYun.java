package com.ptteng.academy.framework.config;

import com.google.gson.Gson;
import com.ptteng.academy.framework.property.QiNiuYunProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static com.ptteng.academy.business.consts.CommonConst.DEFAULT_TEMP_DIR;

/**
 * @program: canary
 * @description: 七牛云配置
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-27 22:55
 **/

@Slf4j
@Configuration
public class QiNiuYun {
    @Resource
    private QiNiuYunProperties qiuNin;

    @Resource
    private TaskConfig taskConfig;

    private com.qiniu.storage.Configuration cfg;
    private String upToken;

    private Auth auth;
    private UploadManager uploadManager;
    private BucketManager bucketManager;

    /**
     * @description 七牛SDK认证对象
     */
    private Auth getAuth(){
        try {
            return Auth.create(qiuNin.getAccess_key_id(), qiuNin.getAccess_key_secret());
        }catch (Exception e){
            e.printStackTrace();
            log.debug("七牛 SDK 认证失败");
            return null;
        }
    }

    /**
     * @description 获取OSS指定地区节点配置
     */
    private com.qiniu.storage.Configuration getCfg(){
        // 使用反射获取对应api方法
        String className = "com.qiniu.common.Zone";
        String methodName = qiuNin.getEndpoint();

        try {
            // 实例化对象
            Class<?> clz = Class.forName(className);
            // 获取方法 clz.getMethod(方法名, 方法接收参数类型);
            Method method = clz.getMethod(methodName);
            // 执行方法
            Zone zone = (Zone) method.invoke(null);
            return new com.qiniu.storage.Configuration(zone);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.debug("Zone类路径无法找到");
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.debug("Zone实例化失败");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.debug("找不到此方法");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            log.debug("调用方法或构造方法所抛出异常的受检查异常");
        }
        return null;
    }

    private UploadManager getUploadManager(){
        cfg = getCfg();
        if(cfg!=null){
            return new UploadManager(cfg);
        }
        return null;
    }

    /**
     * @description 获取bucket管理器对象
     */
    private BucketManager getBucketManager(){
        auth = getAuth();
        cfg = getCfg();
        if(auth!=null&&cfg!=null){
            try {
                return new BucketManager(auth, cfg);
            }catch (Exception e){
                e.printStackTrace();
                log.debug("bucket管理器初始化失败");
            }
        }
        return null;
    }

    /* 上传文件 */
    public String updateMultipartFile(MultipartFile multfile, String fileName) throws IOException {
        /*CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();*/
        return updateFileReal(multfile.getInputStream(), fileName);
    }
    public String updateFileName(String fileName) throws FileNotFoundException {
        File file = new File(DEFAULT_TEMP_DIR + fileName);
        log.debug("上传文件: " + file.getPath());
        if (file.isFile() && file.exists()) {
            InputStream targetStream = new FileInputStream(file);
            return updateFileReal(targetStream, fileName);
        }
        return null;
    }

    public String updateFileReal(InputStream inputStream, String file_name) {
        auth = getAuth();
        if (auth!=null) {
            uploadManager = getUploadManager();
            if(uploadManager!=null){
                try {
                    upToken = auth.uploadToken(qiuNin.getBucket_name());
                    Response qresponse = uploadManager.put(inputStream,file_name,upToken,null, null);
                    if (qresponse.isOK()) {
                        // 解析上传结果
                        DefaultPutRet putRet = new Gson().fromJson(qresponse.bodyString(), DefaultPutRet.class);
                        log.debug("上传结果: fileName: " + putRet.key + " hash: " + putRet.hash);

                        // 注意 map中的值不能直接覆盖 这里直接删掉重写一个马上过期的值即可
                        taskConfig.getFileList().remove(file_name);
                        taskConfig.setFileList(file_name, new Date());

                        return qiuNin.getFile_url() + file_name;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.debug("七牛SDK api 连接失败");
                }
            }
        }
        return null;
    }


    /* 删除 */
    public Boolean deleteFile(String keyFile){
        return deleteReal(qiuNin.getBucket_name(), keyFile);
    }
    public Boolean deleteFile(String bucket_name, String keyFile){
        return deleteReal(bucket_name, keyFile);
    }
    private Boolean deleteReal(String bucket_name, String keyFile) {
        bucketManager = getBucketManager();
        if(bucketManager!=null){
            try {
                bucketManager.delete(bucket_name, keyFile);
                return true;
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
