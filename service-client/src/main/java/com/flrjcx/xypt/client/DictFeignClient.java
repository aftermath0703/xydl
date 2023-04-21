package com.flrjcx.xypt.client;

import com.flrjcx.xypt.common.model.result.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * feign远程调用
 * 使用方法:
 *      把需要远程调用的接口复制过来,把路径补全即可
 * @author Flrjcx
 */
@FeignClient("campus-service-common")
public interface DictFeignClient {
    /**
     * 远程调用测试示例
     *
     * @return
     */
    @GetMapping("/xypt/api/common/verificationcode")
    ResponseData createVerificationCode();
}
