package com.gaoice.system.user.service;

import com.gaoice.system.user.entity.UserDO;
import com.gaoice.system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "jpaTM1", rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDO saveUser(UserDO user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserDO user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserDO user = new UserDO();
        user.setId(id);
        userRepository.delete(user);
    }

    @Override
    public UserDO findUserByUsernameIs(String username) {
        return userRepository.findByUsernameIs(username);
    }

}
