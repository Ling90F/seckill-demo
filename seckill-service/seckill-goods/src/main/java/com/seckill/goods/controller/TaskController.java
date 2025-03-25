package com.seckill.goods.controller;

import com.seckill.goods.task.dynamic.DynamicJob;
import com.seckill.goods.task.dynamic.ElasticJobDynamicConfig;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName TaskController
 * @Description 动态定时任务测试
 * @Version v1.0
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private ElasticJobDynamicConfig elasticjobDynamicConfig;

    /**
     * 动态定时任务案例测试
     */
    @GetMapping
    public Result task(String jobName, Long time, String id) {
        String cron =ElasticJobDynamicConfig.date2Cron(new Date(System.currentTimeMillis()+time));

        elasticjobDynamicConfig.addDynamicTask(jobName, cron, 1, new DynamicJob(),id);
        return new Result(true, StatusCode.OK, "执行成功！");
    }
}
