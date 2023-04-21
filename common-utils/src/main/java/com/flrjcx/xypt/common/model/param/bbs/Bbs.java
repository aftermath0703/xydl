package com.flrjcx.xypt.common.model.param.bbs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author : aftermath
 * @date : 2022-11-04 10:12:47
 */
@Data
public class Bbs implements Serializable {
    private static final long serialVersionUID = -4052493239475027301L;

    @ApiModelProperty(value = "主键id", name = "bbsId")
    private Long bbsId;
    @ApiModelProperty(value = "主键id备用", name = "bbsIdTwo")
    private String bbsIdTwo;
    @ApiModelProperty(value = "发帖用户id", name = "bbsUserId")
    private Long bbsUserId;
    @ApiModelProperty(value = "发帖用户id备用", name = "bbsUserIdTwo")
    private String bbsUserIdTwo;
    @ApiModelProperty(value = "标题", name = "bbsTitle")
    private String bbsTitle;
    @ApiModelProperty(value = "内容markdown格式", name = "bbsContextMd")
    private String bbsContextMd;
    @ApiModelProperty(value = "内容", name = "bbsContext")
    private String bbsContext;
    @ApiModelProperty(value = "浏览量", name = "bbsViews")
    private Integer bbsViews;
    @ApiModelProperty(value = "收藏数量", name = "bbsCollect")
    private Integer bbsCollect;
    @ApiModelProperty(value = "赞数量", name = "bbsPraise")
    private Integer bbsPraise;
    @ApiModelProperty(value = "踩数量", name = "bbsNo")
    private Integer bbsNo;
    @ApiModelProperty(value = "类型", name = "type")
    private Integer bbsType;
    @ApiModelProperty(value = "更新时间", name = "bbsUpdateTime")
    private Date bbsUpdateTime;
    @ApiModelProperty(value = "创建时间", name = "bbsCreateTime")
    private Date bbsCreateTime;
    @ApiModelProperty(value = "删除标记", name = "bbsDelete")
    private int bbsDelete;
    @ApiModelProperty(value = "文章描述", name = "bbsDescription")
    private String bbsDescription;
    @ApiModelProperty(value = "文章封面", name = "bbsCoverPic")
    private String bbsCoverPic;
    @ApiModelProperty(value = "文章标签", name = "bbsLabel")
    private List<String> bbsLabel;
    @ApiModelProperty(value = "文章标签Json串", name = "bbsLabelJson")
    private String bbsLabelJson;
    @ApiModelProperty(value = "作者头像", name = "accountPic")
    private String accountPic;
    @ApiModelProperty(value = "作者昵称", name = "nickName")
    private String nickName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bbs bbs = (Bbs) o;
        return bbsId.equals(bbs.bbsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bbsId);
    }
}
