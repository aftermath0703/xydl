package com.flrjcx.xypt.common.model.param.comment;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 评论实体类
 *
 * @author Yyyyyyy
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = -1155012494731516296L;

    @ApiModelProperty(value = "主键id", name = "commentId")
    private String commentId;
    @ApiModelProperty(value = "帖子id", name = "commentBbsId")
    private Long commentBbsId;
    @ApiModelProperty(value = "用户id", name = "commentUserId")
    private Long commentUserId;
    @ApiModelProperty(value = "评论内容", name = "commentContext")
    private String commentContext;
    @ApiModelProperty(value = "评论时间", name = "commentCreateTime")
    private Date commentCreateTime;
    @ApiModelProperty(value = "评论等级", name = "level")
    private Integer level;
    @ApiModelProperty(value = "评论楼层", name = "commentFloor")
    private Integer commentFloor;
    @ApiModelProperty(value = "父评论id", name = "commentParentId")
    private String commentParentId;
    @ApiModelProperty(value = "子评论", name = "children")
    private List <Comment> children;
    @ApiModelProperty(value = "评论人昵称", name = "nickName")
    private String nickName;
    @ApiModelProperty(value = "评论人头像", name = "nickPic")
    private String nickPic;
    @ApiModelProperty(value = "指定评论人昵称", name = "nickName")
    private String replyNickName;
    @ApiModelProperty(value = "指定评论人头像", name = "nickPic")
    private String replyNickPic;
}
