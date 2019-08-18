package com.gaoice.system.common;

import com.gaoice.system.SystemApplication;
import com.gaoice.system.common.async.AsyncTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class AsyncTests {

    @Autowired
    AsyncTest asyncTest;

    @Test
    public void test() {
    }
}
