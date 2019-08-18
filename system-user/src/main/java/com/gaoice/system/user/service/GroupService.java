package com.gaoice.system.user.service;

import com.gaoice.system.user.entity.GroupDO;
import com.gaoice.system.user.vo.GroupTreeNode;

public interface GroupService {

    void addGroup(GroupDO group);

    void removeGroup(GroupDO group);

    void removeGroup(Long id);

    GroupTreeNode getGroupTreeNodeById(Long id, int level);

    void buildGroupTreeNode(GroupTreeNode node, int level);

}
