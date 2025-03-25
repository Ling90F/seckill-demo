package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NettyWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyWebSocketApplication.class,args);
    }

}
