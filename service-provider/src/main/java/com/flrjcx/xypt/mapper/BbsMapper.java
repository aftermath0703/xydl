package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.model.param.bbs.Bbs;
import com.flrjcx.xypt.common.model.param.bbs.BbsEditParam;
import com.flrjcx.xypt.common.model.param.bbs.BbsHot;
import com.flrjcx.xypt.common.model.param.common.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : aftermath
 * @date : 2022-11-04 10:04:44
 */
public interface BbsMapper {
    /**
     * bbs_praise字段+1
     *
     * @param bbsId 帖子id
     * @return 字段更新是否成功
     */
    boolean updatePraise(@Param("bbsId") Long bbsId);

    /**
     * bbs_praise字段-1
     *
     * @param bbsId 帖子id
     * @return 字段更新是否成功
     */
    boolean cancelPraise(@Param("bbsId") Long bbsId);

    /**
     * bbs_no字段+1
     *
     * @param bbsId 帖子id
     * @return 字段更新是否成功
     */
    boolean updateNo(@Param("bbsId") Long bbsId);

    /**
     * bbs_no字段-1
     *
     * @param bbsId 帖子id
     * @return 字段更新是否成功
     */
    boolean cancelNo(@Param("bbsId") Long bbsId);

    /**
     * 更新帖子
     *
     * @param param 更新帖子对象
     * @return 影响行数
     */
    int updatePost(BbsEditParam param);

    /**
     * 获得发帖人
     *
     * @param bbsId 帖子id
     * @return 发帖人id
     */
    Long selectBbsOwner(Long bbsId);

    /**
     * 根据id删除帖子
     *
     * @param bbsId 帖子id
     * @return 影响行数
     */
    int deletePostById(Long bbsId);

    /**
     * 根据关键词搜索帖子 标题模糊查询
     *
     * @param searchKeys 关键词List
     * @param type 查询类型
     * @return 帖子集合
     */
    List<Bbs> searchPostByKeys(@Param("searchKeys") List<String> searchKeys,@Param("type") Integer type);

    /**
     * 发帖
     *
     * @param bbs
     */
    void production(@Param("bbs") Bbs bbs);

    /**
     * 查询帖子列表
     *
     * @return
     */
    @OpenPage
    List<Bbs> bbsList();

    /**
     * 查询帖子详情
     *
     * @param bbsId
     * @return
     */
    Bbs bbsDetails(@Param("bbsId")long bbsId);

    /**
     * 查询用户热门文章
     *
     * @param userId
     * @return
     */
    List<BbsHot> bbsUserHot(@Param("userId")long userId);

    /**
     * 查询帖子作者昵称和头像
     *
     * @param userId
     * @return
     */
    Users selectAccount(@Param("userId")long userId);
}
