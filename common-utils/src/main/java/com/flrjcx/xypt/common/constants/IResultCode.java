package com.flrjcx.xypt.common.constants;

/**
 * Created on 2019年1月5日 下午3:06:54
 * <p>Title:       [公共model]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Company:     羚羊极速</p>
 *
 * @author [wangyunliang]
 * @version 1.0
 */
public interface IResultCode {
    /**
     * Created on 2019年1月5日
     * <p>Discription:[获取错误码code]</p>
     *
     * @return
     * @author:[wangyunliang]
     */
    public Integer getCode();

    /**
     * Created on 2019年1月5日
     * <p>Discription:[获取错误码信息]</p>
     *
     * @return
     * @author:[wangyunliang]
     */
    public String getMessage();
}
