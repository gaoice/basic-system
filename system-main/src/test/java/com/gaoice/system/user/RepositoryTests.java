package com.gaoice.system.user;

import com.gaoice.system.SystemApplication;
import com.gaoice.system.user.mapper.UserMapper;
import com.gaoice.system.user.repository.GroupRepository;
import com.gaoice.system.user.repository.RoleRepository;
import com.gaoice.system.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 用于测试 jpa 语句
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class RepositoryTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    GroupRepository groupRepository;

    @Resource
    UserMapper userMapper;

    @Test
    public void test() {
    }

    @Test
    public void jpaDelete() {
    }


    @Test
    public void mybatisTest() {
    }

}
