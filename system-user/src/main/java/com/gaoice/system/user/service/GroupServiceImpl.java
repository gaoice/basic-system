package com.gaoice.system.user.service;

import com.gaoice.system.user.entity.GroupDO;
import com.gaoice.system.user.repository.GroupRepository;
import com.gaoice.system.user.vo.GroupTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 事务细粒度至方法级别，事务设置详见具体方法
 */
@Service
@CacheConfig(cacheNames = "group")
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    /**
     * 添加 group ，涉及一下操作：
     * 父 group 的 childNum 加 1，并保存 （update）
     * 保存 group （insert）
     * 生成 group 的 path 并保存 （update）
     * <p>
     * 事务的隔离级别，明确可重复读，避免 Oracle 等数据库默认读已提交级别，数据并发情况下 childNum 计算错误
     * <p>
     * cache 缓存清除设置为全部清除，如果不设置 allEntries = true ，注意使用 SpEL 手动指定 key
     * key 用的序列化器是 jdk 序列化器，如果参数是对象却没有实现 Serializable 接口会报错
     */
    @Override
    @Transactional(transactionManager = "jpaTM1", isolation = Isolation.REPEATABLE_READ)
    @CacheEvict(allEntries = true)
    public void addGroup(GroupDO group) {
        Long parentId = group.getParentId();
        String path = GroupDO.PATH_FIX;
        if (parentId != null) {
            Optional<GroupDO> optional = groupRepository.findById(parentId);
            if (!optional.isPresent()) {
                throw new NoSuchElementException("No such parentId");
            }
            GroupDO parent = optional.get();
            parent.incrementChildNum();
            groupRepository.save(parent);
            path = parent.getPath();
        }
        groupRepository.save(group);
        group.setPath(path + group.getId() + GroupDO.PATH_FIX);
        groupRepository.save(group);
    }

    /**
     * 删除 group ，涉及以下操作：
     * 父 group 的 childNum 减 1，并保存 （update）
     * 删除子节点 （delete）
     *
     * @param group 要删除的 group ，id 不能为空
     */
    @Override
    @Transactional(transactionManager = "jpaTM1", isolation = Isolation.REPEATABLE_READ)
    @CacheEvict(allEntries = true)
    public void removeGroup(GroupDO group) {
        Long parentId = group.getParentId();
        if (parentId != null) {
            Optional<GroupDO> optional = groupRepository.findById(parentId);
            if (!optional.isPresent()) {
                throw new NoSuchElementException("No such parentId");
            }
            GroupDO parent = optional.get();
            parent.decrementChildNum();
            groupRepository.save(parent);
        }
        groupRepository.delete(group);
    }

    /**
     * 仍然要加事务，避免 this 调用的非代理对象
     *
     * @param id
     */
    @Override
    @Transactional(transactionManager = "jpaTM1", isolation = Isolation.REPEATABLE_READ)
    @CacheEvict(allEntries = true)
    public void removeGroup(Long id) {
        GroupDO group = new GroupDO();
        group.setId(id);
        this.removeGroup(group);
    }

    /**
     * 获取节点，节点是树形结构
     *
     * @param id    节点 group 的 id
     * @param level 树形结构的层级，当前节点是 0 层
     * @return 构造好的节点
     */
    @Override
    @Transactional(transactionManager = "jpaTM1", readOnly = true)
    @Cacheable
    public GroupTreeNode getGroupTreeNodeById(Long id, int level) {
        Optional<GroupDO> optional = groupRepository.findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        GroupTreeNode node = new GroupTreeNode();
        node.setGroup(optional.get());
        buildGroupTreeNode(node, level);
        return node;
    }

    /**
     * 对节点进行构建
     *
     * @param node  要构建的节点，node.group.id 字段是必须的，其他字段不必要
     * @param level 要构建的级数，<=0 直接返回，1 只构建 node.childList ，2 递归构建，以此类推，若要完全构建，可以使用 999 等值
     */
    @Override
    @Transactional(transactionManager = "jpaTM1", readOnly = true)
    public void buildGroupTreeNode(GroupTreeNode node, int level) {
        if (level <= 0 || node == null || node.getGroup() == null || node.getGroup().getId() == null) {
            return;
        }
        List<GroupDO> groups = groupRepository.findByParentIdOrderByDisplayOrderAsc(node.getGroup().getId());
        if (groups.size() <= 0) {
            return;
        }
        level--;
        List<GroupTreeNode> childList = new LinkedList<>();
        for (GroupDO group : groups) {
            GroupTreeNode childNode = new GroupTreeNode();
            childNode.setGroup(group);
            //避免不必要的 select 语句
            if (group.getChildNum() > 0) {
                buildGroupTreeNode(childNode, level);
            }
            childList.add(childNode);
        }
        node.setChildList(childList);
    }
}
