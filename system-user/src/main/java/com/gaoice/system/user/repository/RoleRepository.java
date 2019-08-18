package com.gaoice.system.user.repository;

import com.gaoice.system.user.entity.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleDO, Long> {

    RoleDO findByRoleNameIs(String name);

}
