package com.flrjcx.xypt.common.model.param.focus;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 展示用户列表实体类
 *
 * @author Flrjcx
 */
@Data
public class UserPartList {
    @ApiModelProperty(value = "用户昵称", name = "nickName")
    @JsonProperty("nick_name")
    private String nickName;
    @ApiModelProperty(value = "用户头像", name = "nickPic")
    @JsonProperty("nick_pic")
    private String nickPic;
    @ApiModelProperty(value = "用户头像", name = "content")
    private String content;
    @ApiModelProperty(value = "粉丝数量", name = "fansNum")
    @JsonProperty("fans_num")
    private String fansNum;
}
