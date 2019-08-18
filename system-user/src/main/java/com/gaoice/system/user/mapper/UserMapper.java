package com.gaoice.system.user.mapper;

import com.gaoice.system.user.entity.UserDO;

public interface UserMapper {

    UserDO findUserById(Long id);

    int saveUser(UserDO user);

}
