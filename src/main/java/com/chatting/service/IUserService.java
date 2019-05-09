package com.chatting.service;

import com.chatting.model.Friend;
import com.chatting.model.User;

import java.util.List;


public interface IUserService {
    User verifyPassword(String name, String password);
    List<Friend> getFriends(String uuid);
}
