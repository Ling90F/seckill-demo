package com.seckill.goods.task.dynamic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GetSpringBean implements ApplicationContextAware {

    // 声明一个静态变量用于保存spring容器上下文
    @Autowired
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T get(Class<T> clazz) {
        return context.getBean(clazz);
    }
}