package com.gaoice.system.user.security;

import com.gaoice.system.user.entity.RoleDO;
import com.gaoice.system.user.entity.UserDO;
import com.gaoice.system.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * security 授权服务
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("loadUserByUsername : " + username);
        UserDO user = userRepository.findByUsernameIs(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<RoleDO> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleDO role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

}
