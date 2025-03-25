package com.seckill.monitor.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.seckill.goods.feign.SkuFeign;
import com.seckill.monitor.hot.MonitorItemsAccess;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ElasticSimpleJob(
        cron = "0/5 * * * * ?",
        jobName = "monitortask",
        shardingTotalCount = 1
)
public class MonitorTask implements SimpleJob {

    @Autowired
    private MonitorItemsAccess monitorItemsAccess;

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 执行业务逻辑
     */
    @SneakyThrows
    @Override
    public void execute(ShardingContext shardingContext) {
        List<String> ids = monitorItemsAccess.loadData();
        //热点数据隔离
        if (!ids.isEmpty()) {
            skuFeign.hotIsolation(ids);
        }
    }
}