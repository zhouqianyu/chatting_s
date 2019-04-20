package com.chatting.service;

import org.springframework.stereotype.Service;


public interface UserService {
    String getPasswordByUserName(String username);
}
