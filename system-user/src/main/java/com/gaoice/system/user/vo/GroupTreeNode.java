package com.gaoice.system.user.vo;

import com.gaoice.system.user.entity.GroupDO;

import java.util.List;

public class GroupTreeNode {

    private GroupDO group;

    private List<GroupTreeNode> childList;

    public GroupDO getGroup() {
        return group;
    }

    public void setGroup(GroupDO group) {
        this.group = group;
    }

    public List<GroupTreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<GroupTreeNode> childList) {
        this.childList = childList;
    }

}
