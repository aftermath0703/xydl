package com.flrjcx.xypt.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/10/26 0:43
 */
@Data
@Component
@ConfigurationProperties(prefix = "email.kafka")
public class EmailConfig {

    private String visitKey;

}
