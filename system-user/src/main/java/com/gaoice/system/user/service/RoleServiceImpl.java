package com.gaoice.system.user.service;

import com.gaoice.system.user.entity.RoleDO;
import com.gaoice.system.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "jpaTM1", rollbackFor = Throwable.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleDO saveRole(RoleDO role) {
        return roleRepository.save(role);
    }

    @Override
    public RoleDO findRoleByRoleNameIs(RoleDO role) {
        return roleRepository.findByRoleNameIs(role.getRoleName());
    }

}
