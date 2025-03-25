package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author
 */
@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
// @SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.seckill.search.feign","com.seckill.page.feign"})
@MapperScan(basePackages = {"com.seckill.goods.dao"})
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }

}
