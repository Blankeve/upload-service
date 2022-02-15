package com.novedu.nov.upload_service.config;

import com.novedu.nov.upload_service.helper.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：juam
 * @date ：2022/1/5 11:21
 * @description：
 * @modified By：
 * @version:
 */
@Configuration
public class SnowFlakeConfig {
    private Long machineId = 1l;
    private Long dataCenterId = 1l;

    @Bean
    public SnowFlake snowFlakeUtils(){
        return new SnowFlake(machineId,dataCenterId);
    }
}
