package com.flrjcx.xypt.common.utils;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.exception.WebServiceEnumException;
import com.flrjcx.xypt.common.model.dto.FileDto;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 七牛云工具类
 * @author aftermath
 */
@Component
public class QiniuUtils {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.accessSecretKey}")
    private String accessSecretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domain}")
    private String domain;


    /**
     * 处理多文件
     * @param fileDtoList 文件实体类列表
     * @return 路径列表
     */
    public Map<String, List<String>> uploadImage2Qiniu(List<FileDto> fileDtoList){
        try {
            Map<String, List<String>> map = new HashMap<>(8);
            List<String> imageUrls = new ArrayList<>();
            fileDtoList.forEach(fileDto -> {
                            MultipartFile file = fileDto.getFile();
                            checkImage(file);
                            imageUrls.add(uploadImage2Qiniu(fileDto));
                        });
            map.put("imageUrls", imageUrls);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传图片到七牛云
     * @param fileDto 文件实体类
     * @return
     */
    private String uploadImage2Qiniu(FileDto fileDto){
        try {
            MultipartFile file = fileDto.getFile();
            String fileName = fileDto.getPath();
            //1、获取文件上传的流
            byte[] fileBytes = file.getBytes();

            //2.构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.huanan());
            UploadManager uploadManager = new UploadManager(cfg);

            //3.获取七牛云提供的 token
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            uploadManager.put(fileBytes,fileName,upToken);

            return domain+"/"+fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void checkImage(MultipartFile multipartFile) {
        if (ObjectUtils.isEmpty(multipartFile)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_NULL_ERROR);
        }
        if (!ImageUtils.isFileSizeQualify(multipartFile)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_SIZE_FAILED);
        }
        if (!ImageUtils.isFileAllowed(multipartFile.getOriginalFilename())) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_FORMATTER_FAILED);
        }
    }

    /**
     * 删除文件
     */
    public void deleteFileFromQiniu(String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        String key = fileName;
        Auth auth = Auth.create(accessKey, accessSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
