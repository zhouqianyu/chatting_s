package com.chatting.dao;

import com.chatting.model.User;

import java.util.List;

public interface IUserDao {
    User getUserByUsername(String username);
    User getUserByUuid(String uuid);

}
