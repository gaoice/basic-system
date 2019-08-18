package com.gaoice.system.user.repository;


import com.gaoice.system.user.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDO, Long> {

    List<UserDO> findByUsernameLike(String username);

    UserDO findByUsernameIs(String username);

}
