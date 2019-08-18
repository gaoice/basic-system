package com.gaoice.system.common.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TestEventPublisher {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(TestEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
