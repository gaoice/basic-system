package com.gaoice.system.common;

import com.gaoice.system.SystemApplication;
import com.gaoice.system.common.mongo.TestMongoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoTests {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * save
     * 若 id 已存在，则修改对应的值。
     */
    @Test
    public void save() {
        TestMongoEntity test = new TestMongoEntity();
        test.setId(0);
        test.setName("test");
        mongoTemplate.save(test);
    }

    @Test
    public void query() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(1));
        List<TestMongoEntity> list = mongoTemplate.find(query, TestMongoEntity.class);
        if (!CollectionUtils.isEmpty(list)) {
            System.out.println(list.get(0).getName());
        }
    }
}
