package com.chatting.service;

import com.chatting.model.User;


public interface IUserService {
    User verifyPassword(String name, String password);
}
