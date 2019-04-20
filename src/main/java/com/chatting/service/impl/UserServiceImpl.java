package com.chatting.service.impl;

import com.chatting.dao.UserDao;
import com.chatting.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserDao dao;
    public String getPasswordByUserName(String username) {
        return dao.getPasswordByUsername(username);
    }
}
