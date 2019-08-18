package com.gaoice.system.user.service;

import com.gaoice.system.user.entity.RoleDO;

public interface RoleService {

    RoleDO saveRole(RoleDO role);

    RoleDO findRoleByRoleNameIs(RoleDO role);

}
