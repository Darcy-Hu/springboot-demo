package com.springcloud.web.util;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringConfUtils implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static ConfigurableApplicationContext context;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        context = configurableApplicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
