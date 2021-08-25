package com.springcloud.web.events.subscribers;

import com.springcloud.common.util.ApplicationUtils;
import com.springcloud.web.events.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseSubscriber {

//    @Autowired
//    EventManager eventManager;
//
//    public BaseSubscriber(){
//        eventManager = ApplicationUtils.getBean(EventManager.class);
//        eventManager.registerSubscriber(this);
//    }
}
