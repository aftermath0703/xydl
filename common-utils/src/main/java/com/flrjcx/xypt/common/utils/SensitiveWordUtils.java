package com.flrjcx.xypt.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.SensitiveUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flrjcx.xypt.common.config.SensitiveWordConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: aftermath
 * @Date: 2022-11-02 13:53:54
 * @Desc:
 */
@Component
public class SensitiveWordUtils {
    /**
     * 敏感词过滤, 用'*'替换
     * @param text text 需要检查的文本
     * @param isDensityMatch 是否密集匹配
     *                          1. 如果非密度匹配, 下一次寻找的起始位置会跳过已寻找到的敏感词
     *                          2. 如果密度匹配, 下一次寻找的起始位置不变, 直到尾节点遍历到终点
     *                          3. 密集false 默认贪婪也是false
     * @param isGreedMatch 是否贪婪匹配(找到一个匹配项之后是否还继续找)
     * @return 返回替换掉敏感词的文本
     */
    public static String sensitiveWordFiltering(String text, boolean isDensityMatch, boolean isGreedMatch) {
        List<FoundWord> matchAll = SensitiveUtil.getFoundAllSensitive(text, isDensityMatch, isGreedMatch);
        if (matchAll.size() > 0) {
            for (FoundWord match : matchAll) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < StrUtil.length(match.getFoundWord()); i++) {
                    sb.append("*");
                }
                text = StrUtil.replace(text, match.getFoundWord(), sb.toString());
            }
        }
        return text;
    }

    /**
     * 检查是否包含敏感词
     * @param text 要检查的文本
     * @return 包含敏感词返回true
     */
    public static boolean sensitiveWordCheck(String text) {
        return SensitiveUtil.containsSensitive(text);
    }
}
