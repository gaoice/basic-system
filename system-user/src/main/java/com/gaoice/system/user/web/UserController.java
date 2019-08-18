package com.gaoice.system.user.web;

import com.gaoice.system.user.entity.UserDO;
import com.gaoice.system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user/page/{page}")
    public Page<UserDO> listUser(@PathVariable int page, Integer pageSize) {
        if (pageSize == null) {
            pageSize = 10;
        }
        //前端是从第 1 页，数据库是从第 0 页
        page--;
        if (page < 0) {
            page = 0;
        }
        //排序
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return userRepository.findAll(pageable);
    }

}
