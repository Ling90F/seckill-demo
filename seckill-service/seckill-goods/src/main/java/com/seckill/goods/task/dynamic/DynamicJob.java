package com.seckill.goods.task.dynamic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DynamicJob
 * @Description 编写定时任务业务处理方法
 * @Version v1.0
 */
public class DynamicJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String jobName = shardingContext.getJobName();
        System.out.println(jobName + "时间" + simpleDateFormat.format(new Date())+":::"+shardingContext.getJobParameter());
    }
}
