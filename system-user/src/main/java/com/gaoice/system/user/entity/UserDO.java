package com.gaoice.system.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gaoice.system.user.enumeration.Sex;
import com.gaoice.system.user.enumeration.Status;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * 将会生成两张表：
 * com_gao_ice_user 用户表
 * com_gao_ice_user_role 用户角色关系表，由 jpa 自动维护
 * <p>
 * 懒加载导致使用 DO 对象作为 Controller 返回值会报错，可以使用 @JsonIgnoreProperties 注解，或者转换为 VO 对象
 */
@Entity
@Table(name = "com_gao_ice_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")},
        indexes = {@Index(columnList = "username")})
@JsonIgnoreProperties(value = {"password", "group", "roles"})
public class UserDO {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(generator = "native")
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    private int sex = Sex.UNKNOWN.getValue();
    private String email;
    private int status = Status.ENABLED.getValue();

    /**
     * 用户 组 多对一关系
     * referencedColumnName 可以指定被关联实体的外键，不指定则默认是主键
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupDO group;
    /**
     * 用户 角色 多对多关系
     * 关系表由 jpa 自动维护
     * cascade = CascadeType.ALL，关联 com_gao_ice_user 所有级联操作
     * FetchType.LAZY 懒加载，getRoleList() 时候才加载，要在同一个 session 中，否则会执行失败
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "com_gao_ice_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<RoleDO> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public GroupDO getGroup() {
        return group;
    }

    public void setGroup(GroupDO group) {
        this.group = group;
    }

    public List<RoleDO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDO> roles) {
        this.roles = roles;
    }

}
