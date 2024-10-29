package com.seckill.config;

import com.seckill.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author http://www.itheima.com
 */
@Configuration
public class AutoConfig {

    /***
     * 创建IdWorker
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

}
