package com.gaoice.system.user.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "com_gao_ice_group")
public class GroupDO {

    public static final String PATH_FIX = ",";

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(generator = "native")
    private Long id;

    private String groupName;
    private Long parentId;

    /**
     * 使用 path 快速判断 group 的隶属关系，最顶层 group 的 path 为 ,1,
     * 如果一个 group 的 path 为 ,1,3,5,7,9, 可以使用 like '%,7,%' 筛选 id 为 7 的节点以及 7 的子节点，而不必递归查询
     * 缺点：添加 group 和更改 group 时候此字段需要维护
     */
    private String path;

    private Integer childNum = 0;
    /**
     * 显示顺序，select 语句的 order by
     */
    private Integer displayOrder = 100;

    public void incrementChildNum() {
        if (childNum == null) {
            childNum = 0;
        }
        childNum++;
    }

    public void decrementChildNum() {
        if (childNum == null || childNum <= 0) {
            childNum = 0;
            return;
        }
        childNum--;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}
