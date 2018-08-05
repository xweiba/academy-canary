package com.ptteng.academy.util;

import com.ptteng.academy.framework.config.TaskConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.ptteng.academy.business.consts.CommonConst.DEFAULT_TEMP_DIR;


/**
 * @program: canary
 * @description: 文件保存工具类
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-28 13:10
 **/
@Slf4j
@Component
public class FileSaveUtil {
    @Resource
    private TaskConfig taskConfig;

    public String saveFile(MultipartFile multipartFile) throws Exception {

        if (multipartFile == null) {
            return null;
        }

        String file_name = MD5Util.getMultipartFileMd5(multipartFile) + UpdateFileUtil.getFileExt(multipartFile.getContentType());
        String save_path = DEFAULT_TEMP_DIR + file_name;
        File file = new File(save_path);
        log.info("save_path路径: " + file.getPath());
        multipartFile.transferTo(file);
        // 图片保存半个小时
        taskConfig.setFileList(file_name, new Date(new Date().getTime() + 1800000));
        // taskConfig.setFileList(file_name, new Date(new Date().getTime() + 5000));
        log.info("saveFile 保存成功");
        return file_name;
    }
}
