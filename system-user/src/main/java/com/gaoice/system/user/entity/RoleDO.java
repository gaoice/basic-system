package com.gaoice.system.user.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "com_gao_ice_role")
public class RoleDO {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(generator = "native")
    private Long id;
    @Column(nullable = false)
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
