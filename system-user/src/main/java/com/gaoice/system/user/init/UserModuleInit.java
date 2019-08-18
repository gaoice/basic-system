package com.gaoice.system.user.init;

import com.gaoice.system.user.entity.GroupDO;
import com.gaoice.system.user.entity.RoleDO;
import com.gaoice.system.user.entity.UserDO;
import com.gaoice.system.user.repository.GroupRepository;
import com.gaoice.system.user.repository.RoleRepository;
import com.gaoice.system.user.repository.UserRepository;
import com.gaoice.system.user.vo.GroupTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 初始化 user module
 * 向数据库中
 * 添加 root 组
 * 添加 admin 角色
 * 添加 admin 用户
 * <p>
 * 初始化 GroupTree
 */
@Component
@Order
public class UserModuleInit implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserModuleInit.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GroupTree groupTree;

    @Override
    @Transactional(transactionManager = "jpaTM1")
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("system-user 初始化开始");
        /*
         * 添加部门
         */
        Optional<GroupDO> optionalGroup = groupRepository.findById(1L);
        GroupDO group = null;
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
            LOGGER.info("已存在 id 为 1 的组");
        } else {
            group = new GroupDO();
            group.setGroupName("root");
            group.setPath(",1,");
            group.setDisplayOrder(1);
            groupRepository.save(group);
            LOGGER.info("添加 root 组成功");
        }
        /*
         * 添加角色
         */
        Optional<RoleDO> optionalRole = roleRepository.findById(1L);
        RoleDO role = null;
        if (optionalRole.isPresent()) {
            role = optionalRole.get();
            LOGGER.info("已存在 id 为 1 的角色");
        } else {
            role = new RoleDO();
            role.setRoleName("admin");
            roleRepository.save(role);
            LOGGER.info("添加 admin 角色成功");
        }
        /*
         * 添加用户
         */
        UserDO user = userRepository.findByUsernameIs("admin");
        if (user == null) {
            user = new UserDO();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setNickname("admin");
            user.setGroup(group);
            List<RoleDO> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
            LOGGER.info("添加 admin 用户成功");
        } else {
            LOGGER.info("已存在用户名为 admin 的用户");
        }
        groupTree.init();
        LOGGER.info("system-user 初始化结束");
    }

}
