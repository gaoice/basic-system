package com.gaoice.system.user;

import com.gaoice.system.SystemApplication;
import com.gaoice.system.user.entity.GroupDO;
import com.gaoice.system.user.entity.UserDO;
import com.gaoice.system.user.service.GroupService;
import com.gaoice.system.user.service.RoleService;
import com.gaoice.system.user.service.UserService;
import com.gaoice.system.user.vo.GroupTreeNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用于测试 Service
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class ServiceTests {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    GroupService groupService;

    @Test
    public void findUser() {
    }

    @Test
    public void addGroup() {
        Queue<Long> queue = new LinkedList<>();
        GroupTreeNode root = groupService.getGroupTreeNodeById(1L, 999);
        queue.offer(root.getGroup().getId());
        for (int i = 0; i < 1; i++) {
            Long id = queue.poll();
            GroupDO group = new GroupDO();
            group.setParentId(id);
            group.setGroupName(id + ".1");
            GroupDO group2 = new GroupDO();
            group2.setParentId(id);
            group2.setGroupName(id + ".2");
            GroupDO group3 = new GroupDO();
            group3.setParentId(id);
            group3.setGroupName(id + ".3");
            groupService.addGroup(group);
            groupService.addGroup(group2);
            groupService.addGroup(group3);
            queue.offer(group.getId());
            queue.offer(group2.getId());
            queue.offer(group3.getId());
        }
    }

    @Test
    public void addGroup2() {
        GroupTreeNode root = groupService.getGroupTreeNodeById(1L, 999);
        groupService.addGroup(root.getGroup());
    }

    @Test
    public void transaction() {
    }

}
