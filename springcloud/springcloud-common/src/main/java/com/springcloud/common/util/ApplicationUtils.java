package com.springcloud.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

//ApplicationContextAware,
@Component
public class ApplicationUtils implements ApplicationContextInitializer<ConfigurableApplicationContext> {

//    private static ApplicationContext applicationContext;

    private static ConfigurableApplicationContext context;

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

//    public static <T> T getBeanByApplicationContext(Class<T> clazz){
//        return applicationContext.getBean(clazz);
//    }
//
//    public static <T> T getBean(String name, Class<T> type){
//        return applicationContext.getBean(name,type);
//    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        context = configurableApplicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
