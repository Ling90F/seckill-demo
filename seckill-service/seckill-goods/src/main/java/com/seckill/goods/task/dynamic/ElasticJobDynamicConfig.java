package com.seckill.goods.task.dynamic;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName ElasticJobDynamicConfig
 * @Description 编写配置类，实现添加动态任务
 * @Version v1.0
 */
@Configuration
public class ElasticJobDynamicConfig {
    @Value("${zkserver}")
    private String zkserver;
    @Value("${zknamespace}")
    private String zknamespace;

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    private DynamicListener dynamicListener;

    /**
     * 监听器
     */
    @Bean
    public DynamicListener dynamicListener() {
        return new DynamicListener(10000L, 100000L);
    }

    // 1、配置初始化数据
    @Bean
    public ZookeeperConfiguration zkConfig() {
        return new ZookeeperConfiguration(zkserver, zknamespace);
    }

    // 2、注册初始化数据
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter(ZookeeperConfiguration zkConfig) {
        return new ZookeeperRegistryCenter(zkConfig);
    }

    // 3、动态添加定时任务
    public void addDynamicTask(String jobName, String cron, int shardingTotalCount,
                               SimpleJob instance, String id) {
        //1.添加Elastjob-lite的任务作业器
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(
                new SimpleJobConfiguration(
                        JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                                .jobParameter(id)  //额外的参数
                                .build(),
                        instance.getClass().getName())
        ).overwrite(true).build();//overwrite(true)覆盖原来同名的任务

        //2.将Lite的任务作业器添加到Spring的任务启动器中，并初始化
        new SpringJobScheduler(instance, zookeeperRegistryCenter, liteJobConfiguration,dynamicListener).init();
    }


    /**
     * @Description 额外内容：时间转换为cron表达式 - 例："0/5 * * * * ?"
     **/

    //cron表达式格式
    private static final String cron = "ss mm HH dd MM ? yyyy";

    public static String date2Cron(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(cron);
        return simpleDateFormat.format(date);
    }
}
