package com.flrjcx.xypt.controller;


import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.exception.WebServiceEnumException;
import com.flrjcx.xypt.common.exception.WebServiceException;
import com.flrjcx.xypt.common.model.dto.FileDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import com.flrjcx.xypt.service.FileService;
import com.flrjcx.xypt.service.ManageUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author aftermath   10-28-23:00
 */
@Api(tags = "文件上传")
@ApiRestController("/fileUploader")
@Log4j2
public class FileUploaderController {
    @Resource
    ManageUserInfoService manageUserInfoService;
    @Resource
    TokenService tokenService;
    @Resource
    FileService fileService;

    //TODO: 图像分类后面可以考虑单独建一个表（redis/mysql）进行管理，暂时用不到，暂时搁置
    /**
     * 单或多图上传
     * @param fileList 文件
     * @param headPathList 头路径
     * @return url集合
     */
    @PostMapping("/uploadPic")
    @ApiOperation(value = "上传图片接口")
    @Validation
    public ResponseData uploadPic(@RequestBody(required = true) List<MultipartFile> fileList,
                                  @RequestParam(required = false) List<String> headPathList) {
        try {
            int fileSize = fileList.size();
            int headPathsSize = headPathList.size();
            if (fileSize < headPathsSize) {
                throw WebServiceEnumException.buildResponseData(ResultCodeEnum.CODE_PARAM_ERROR);
            }

            List<FileDto> fileDtoList = new ArrayList<>();
            for (int i = 0; i < fileSize; i++) {
                if (i<=headPathsSize) {
                    fileDtoList.add(FileDto.builder().file(fileList.get(i)).headPath(headPathList.get(i)).build());
                } else {
                    fileDtoList.add(FileDto.builder().file(fileList.get(i)).build());
                }
            }

            Map<String, List<String>> urlList = fileService.uploadToQiNiu(fileDtoList);
            return ResponseData.buildResponse(urlList);
        } catch (WebServiceException e) {
            log.error("/uploadPic error " + e.getResponseData().getMessage());
            return e.getResponseData();
        }
    }

    /**
     * 上传+修改数据库字段(单图)
     * @param token token字段
     * @param file 文件
     * @return
     */
    @PostMapping("/updateAvatar")
    @ApiOperation(value = "修改用户头像接口")
    @Validation
    public ResponseData updatePic(@RequestHeader("Authorization") String token,
                                  @RequestBody(required = true) MultipartFile file) {
        try {
            if (ObjectUtils.isEmpty(file)) {
                throw WebServiceEnumException.buildResponseData(ResultCodeEnum.ERR_CODE_FILE_NULL_ERROR);
            }
            //上传图片到七牛云
            Map<String, List<String>> uploadImagesUrl = fileService.uploadToQiNiu(FileDto.builder().file(file).headPath("users/avatar").build());
            String url = uploadImagesUrl.get("imageUrls").get(0);
            Users user = UserThreadLocal.get();
            user.setNickPic(url);
            //更新数据库字段
            if (manageUserInfoService.updatePic(user)) {
                tokenService.updateCache(token, user);
            } else {
                log.error("/updatePic update dataBase error");
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_ROLE_UPDATE);
            }
            return ResponseData.buildResponse(uploadImagesUrl);
        } catch (WebServiceException e) {
            log.error("/updatePic error " + e.getResponseData().getMessage());
            return e.getResponseData();
        }
    }
}
