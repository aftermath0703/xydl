package com.flrjcx.xypt.common.config;

import cn.hutool.dfa.SensitiveUtil;
import com.flrjcx.xypt.common.utils.FileUtils;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: aftermath
 * @Date: 2022-11-02 13:42:37
 * @Desc:
 */
@Component
public class SensitiveWordConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordConfig.class);

    /**
     * 初始化敏感词汇
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("dict/harmonious_dictionary/*.txt");
        List<InputStream> inputStreamList = Arrays.stream(resources).map(x -> {
            InputStream inputStream = null;
            try {
                inputStream = x.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }).collect(Collectors.toList());

        InputStream in = FileUtils.mergeInputStream(inputStreamList);
        List<String> list = IOUtils.readLines(in, Charsets.toCharset("UTF-8"));

        in.close();
        inputStreamList.forEach(x -> {
            try {
                x.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("SensitiveWordConfig read lines successfully!");
        SensitiveUtil.init(list);
        logger.info("initialize bean:{}", SensitiveWordConfig.class.getName());
    }

}