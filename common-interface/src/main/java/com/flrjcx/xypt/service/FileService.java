package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.exception.WebServiceException;
import com.flrjcx.xypt.common.model.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件上传服务
 * @author aftermath
 */
public interface FileService {
    /**
     * 单文件上传
     *
     * @param fileDto 文件实体类
     * @return  文件访问地址
     * @throws WebServiceException 错误
     */
    Map<String, List<String>> uploadToQiNiu(FileDto fileDto) throws WebServiceException;

    /**
     * 多文件上传
     *
     * @param fileDto 文件实体类
     * @return  文件访问地址
     * @throws WebServiceException 错误
     */
    Map<String, List<String>> uploadToQiNiu(List<FileDto> fileDto) throws WebServiceException;




}
