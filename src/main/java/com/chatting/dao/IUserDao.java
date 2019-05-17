package com.chatting.dao;

import com.chatting.model.User;
import org.apache.ibatis.annotations.Param;


public interface IUserDao {
    User getUserByUsername(String username);

    User getUserByUuid(String uuid);

    int changeInfo(@Param("user") User user);
}
