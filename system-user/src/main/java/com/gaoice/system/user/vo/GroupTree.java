package com.gaoice.system.user.vo;

import com.gaoice.system.user.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * group 树形结构
 * 树形结构太大影响启动性能，可以调小{@link #loadLevel }的值
 */
@Component
public class GroupTree {

    @Autowired
    private GroupService groupService;

    /**
     * 每一次加载的级数
     * 考虑系统加载性能可以调小此值
     */
    private int loadLevel = 999;

    /**
     * 根节点
     */
    private GroupTreeNode root;

    /**
     * 所有节点的 map ，构建后，避免递归查找某个节点
     */
    private Map<Long, GroupTreeNode> nodeMap;

    /**
     * 获取 group 树形结构
     */
//    @PostConstruct
    public void init() {
        //避免重入
        synchronized (this) {
            root = groupService.getGroupTreeNodeById(1L, loadLevel);
            nodeMap = new HashMap<>();
            /*
             * 广度优先遍历，得到 nodeMap
             */
            Queue<GroupTreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (queue.size() > 0) {
                GroupTreeNode node = queue.poll();
                if (node.getChildList() != null) {
                    queue.addAll(node.getChildList());
                }
                nodeMap.put(node.getGroup().getId(), node);
            }
        }
    }

    /**
     * 返回节点，若节点 childList 为空并且 group.childNum 不为空，则按照 loadLevel 规定的层级为当前节点进行构建
     *
     * @param id 节点 id
     * @return id 对应的节点
     */
    public GroupTreeNode getNode(Long id) {
        if (nodeMap == null) {
            return null;
        }
        GroupTreeNode node = nodeMap.get(id);
        if (node != null && node.getGroup().getChildNum() > 0 && node.getChildList() == null) {
            groupService.buildGroupTreeNode(node, loadLevel);
        }
        return node;
    }

    public GroupTreeNode getRoot() {
        return root;
    }

    public Map<Long, GroupTreeNode> getNodeMap() {
        return nodeMap;
    }

}
