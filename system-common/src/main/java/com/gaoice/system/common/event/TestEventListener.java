package com.gaoice.system.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 也可以使用 @EventListener 注解
 * 默认同步执行
 * 使用 @Async 可异步执行
 */
@Component
public class TestEventListener implements ApplicationListener<TestEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestEventListener.class);

    @Override
    public void onApplicationEvent(TestEvent event) {
        LOGGER.info("implements ApplicationListener<TestEvent> :" + event.getSource().toString());
    }

    @EventListener
    public void testEventListener(TestEvent event) {
        LOGGER.info("@EventListener :" + event.getSource().toString());
    }

}
