package com.heyu.upload.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
图片上传服务层(类)
 */
@Service
public class UploadService {

    //初始化静态集合，定义图片文件拓展名集合
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif", "image/png");

    @Autowired
    private FastFileStorageClient storageClient;
    //定义输出logger日志 输出图片上传失败原因
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    /**
     * @Description: 图片上传方法
     * @Param: [file]
     * @return: java.lang.String
     * @Author: Big Brother
     * @Date: 2020/7/20
     */
    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //校验文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法：{}", originalFilename);
            return null;
        }

        try {
            //校验文件内容
            BufferedImage bufferImage = ImageIO.read(file.getInputStream());
            if (bufferImage == null) {
                LOGGER.info("文件内容不合法：{}", originalFilename);
                return null;
            }

            //保存到服务器
            // file.transferTo(new File("E:\\GraduationProject\\172021139\\image\\" + originalFilename));
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);

            //返回url，进行回显
            //return "http://image.heyu.com/" + originalFilename;
            return "http://image.heyu.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误:" + originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
