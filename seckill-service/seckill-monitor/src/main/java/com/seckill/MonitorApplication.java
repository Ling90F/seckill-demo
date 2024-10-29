package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author http://www.itheima.com
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.seckill.goods.feign"})
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class,args);
    }
}
