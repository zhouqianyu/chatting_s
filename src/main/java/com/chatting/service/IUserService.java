package com.chatting.service;

import com.chatting.model.Friend;
import com.chatting.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserService {
    User verifyPassword(String name, String password);
    List<Friend> getFriends(String uuid);
    User getMyInfo(String uuid);
    int changeInfo(User user);
}
