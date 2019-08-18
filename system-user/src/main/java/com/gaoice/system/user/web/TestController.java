package com.gaoice.system.user.web;

import com.gaoice.system.user.service.GroupService;
import com.gaoice.system.user.vo.GroupTree;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupTree groupTree;

    @RequestMapping("/test/groupTree")
    public GroupTree groupTree() {
        return groupTree;
    }

    @RequestMapping("/test/cache/groupTree")
    public GroupTree cacheGroupTree() {
        groupTree.init();
        return groupTree;
    }

    @RequestMapping("/test/redis/groupTree")
    public Object redisGroupTree() {
        //配置使用 jackson 序列化，不必实现 Serializable 接口
        redisTemplate.opsForValue().set("groupTree", groupTree);
        return redisTemplate.opsForValue().get("groupTree");
    }

    @RequestMapping("/test/test1Queue")
    public String test1Queue() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test1Queue", i + "");
        }
        return "success";
    }

    @RequestMapping("/test/fanoutExchange")
    public String fanoutExchange() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("fanoutExchange", "", i + "");
        }
        return "success";
    }

    @RequestMapping("/test/topicExchange")
    public String topicExchange() {
        for (int i = 0; i < 10; i++) {
            if ((i & 1) == 0) {
                rabbitTemplate.convertAndSend("topicExchange", "test1.a", i + "");
            } else {
                rabbitTemplate.convertAndSend("topicExchange", "test2.a", i + "");
            }
        }
        return "success";
    }
}
