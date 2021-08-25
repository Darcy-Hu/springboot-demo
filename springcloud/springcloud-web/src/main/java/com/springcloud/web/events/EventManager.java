package com.springcloud.web.events;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.springcloud.common.util.ApplicationUtils;
import com.springcloud.web.events.event.Event;
import com.springcloud.web.events.subscribers.BaseSubscriber;
import com.springcloud.web.events.subscribers.SQLSubscriber;
import com.springcloud.web.util.SpringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EventManager {

    private Class[] clsArray = {SQLSubscriber.class};

    private static final String BASE_SUBSCRIBER_PACKAGE = "com/springcloud/web/events/subscribers";

    private EventBus eventBus;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @PostConstruct
    private void init() throws ClassNotFoundException {
        eventBus = new AsyncEventBus("systemEvents",executorService);
        ensureSubscriberEventsAreRegisteredAsBean();
    }

    private void ensureSubscriberEventsAreRegisteredAsBean() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new SubscriberFilter());
        Set<BeanDefinition> components = provider.findCandidateComponents(BASE_SUBSCRIBER_PACKAGE);
//        for (BeanDefinition component: components) {
//             Class cls = Class.forName(component.getBeanClassName());
//            if(SpringUtils.getBean(cls) == null){
//                throw new ClassNotFoundException(cls.getName());
//            }
//        }
//
//        for (int i = 0; i < clsArray.length; i++) {
//            Class cls = clsArray[i];
//            System.out.println(cls.getName());
//            if (SpringUtils.getBean(cls) == null){
//                throw new ClassNotFoundException(cls.getName());
//            }
//        }
    }

    public void publishEvent(Event event) {
        eventBus.post(event);
    }

    public void registerSubscriber(BaseSubscriber baseSubscriber) {
        eventBus.register(baseSubscriber);
    }

    public void unregisterSubscriber(BaseSubscriber baseSubscriber) {
        eventBus.unregister(baseSubscriber);
    }

    private static class SubscriberFilter extends AbstractTypeHierarchyTraversingFilter{

        protected SubscriberFilter() {
            super(true, false);
        }

        @Override
        protected Boolean matchSuperClass(String superClassName){
            return BaseSubscriber.class.getName().equalsIgnoreCase(superClassName);
        }
    }
}
