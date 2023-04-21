package com.flrjcx.xypt.common.config;

import com.flrjcx.xypt.common.utils.SnowflakeIdWorker;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author malaka
 * 雪花ID配置
 */
@Configuration
public class IdWorkerConfiguration {

    @Value("${id.work:noWorkId}")
    private String workId;
    @Value("${id.dateSource:noDateSource}")
    private String dateSource;

    /**
     * 注入雪花id生成对象 SnowflakeIdWorker
     * @return
     */
    @Bean
    @Primary
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(getWorkFromConfig(), getDateFromConfig());
    }

    private Long getWorkFromConfig() {
        if ("noWorkId".equals(workId)) {
            return getWorkId();
        } else {
            //将workId转换为Long
            return 2L;
        }
    }

    private Long getDateFromConfig() {
        if ("noDateSource".equals(dateSource)) {
            return getDataCenterId();
        } else {
            //将workId转换为Long
            return 2L;
        }
    }

    private Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtils.nextLong(0, 31);
        }
    }

    //private Long getDataCenterId() {
    //    int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
    //    int sums = 0;
    //    for (int i : ints) {
    //        sums += i;
    //    }
    //    return (long) (sums % 32);
    //}

    private static Long getDataCenterId() {
        int[] ints = StringUtils.toCodePoints(getHostName());
        int sums = 0;
        for (int i: ints) {
            sums += i;
        }
        return (long)(sums % 32);
    }

    /**
     * 获取 hostName
     *   SystemUtils.getHostName() 在mac 或 linux系统为空处理
     * @return
     */
    public static String getHostName() {
        //获取当前操作系统名称,例如:windows xp,linux 等;
        String osName = System.getProperty("os.name").toLowerCase();
        String hostName = null;
        if(osName.startsWith("mac") || osName.startsWith("linux")){
            try {
                hostName = InetAddress.getLocalHost().getHostName().toUpperCase();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                hostName = "N/A";
            }
        }else{
            hostName = SystemUtils.getHostName();
        }
        return hostName;
    }
}
