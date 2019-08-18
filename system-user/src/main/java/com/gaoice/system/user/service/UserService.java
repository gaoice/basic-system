package com.gaoice.system.user.service;

import com.gaoice.system.user.entity.UserDO;

public interface UserService {
    UserDO saveUser(UserDO user);

    void deleteUser(UserDO user);

    void deleteUser(Long id);

    UserDO findUserByUsernameIs(String username);
}
