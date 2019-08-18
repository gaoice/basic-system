package com.gaoice.system.common;

import com.gaoice.system.SystemApplication;
import com.gaoice.system.common.event.TestEvent;
import com.gaoice.system.common.event.TestEventPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class EventTests {


    @Autowired
    TestEventPublisher testEventPublisher;

    @Test
    public void testEvent() {
        TestEvent testEvent = new TestEvent("o( =•ω•= )m test event");
        testEventPublisher.publishEvent(testEvent);
    }
}
