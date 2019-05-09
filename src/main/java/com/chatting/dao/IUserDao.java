package com.chatting.dao;

import com.chatting.model.User;


public interface IUserDao {
    User getUserByUsername(String username);
    User getUserByUuid(String uuid);
}
