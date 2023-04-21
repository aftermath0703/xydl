package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.exception.WebServiceEnumException;
import com.flrjcx.xypt.common.exception.WebServiceException;
import com.flrjcx.xypt.common.model.dto.FileDto;
import com.flrjcx.xypt.common.utils.QiniuUtils;
import com.flrjcx.xypt.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 疯了~ && aftermath
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    QiniuUtils qiniuUtils;

    @Value("${qiniu.defaultPath}")
    private String defaultPath;

    /**
     * 多文件上传
     *
     * @param fileDtoList 文件实体类
     * @return 文件访问地址
     * @throws WebServiceException 错误
     */
    @Override
    public Map<String, List<String>> uploadToQiNiu(List<FileDto> fileDtoList) throws WebServiceException {
        fileDtoList = fileDtoList.stream()
                .peek(fileDto -> {
                    MultipartFile file = fileDto.getFile();
                    if (ObjectUtils.isEmpty(file)) {
                        throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_NULL_ERROR);
                    }
                    String headPath = fileDto.getHeadPath();
                    if (ObjectUtils.isEmpty(headPath)) {
                        headPath = defaultPath;
                    }
                    fileDto.setPath(alterFileName(file, headPath));
                })
                .collect(Collectors.toList());

        return qiniuUtils.uploadImage2Qiniu(fileDtoList);
    }

    /**
     * 文件上传
     *
     * @param fileDto 文件实体类
     * @return 文件访问地址
     */
    @Override
    public Map<String, List<String>> uploadToQiNiu(FileDto fileDto) throws WebServiceException {
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(fileDto);
        return this.uploadToQiNiu(fileDtoList);
    }

    /**
     * 修改文件名
     * @param file 文件
     * @param headPath 文件头部地址
     * @return 新文件名
     */
    public String alterFileName(MultipartFile file, String headPath) throws WebServiceException {
        //1、创建日期目录分隔
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = dateFormat.format(new Date());

        String originalFilename = file.getOriginalFilename();
        if (ObjectUtils.isEmpty(originalFilename)) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_NAME_NULL_ERROR);
        }
        int lastIndex = originalFilename.lastIndexOf(".");
        if (lastIndex <0) {
            throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_FORMATTER_FAILED);
        }

        String suffix = originalFilename.substring(lastIndex);

        return datePath+"/"+ headPath + "/" + UUID.randomUUID().toString().replace("-", "") + suffix;
    }


}