package com.chatting.service.impl;

import com.chatting.dao.IFriendDao;
import com.chatting.dao.IUserDao;
import com.chatting.model.Friend;
import com.chatting.model.User;
import com.chatting.service.IUserService;
import com.chatting.util.RandomString;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    IUserDao dao;
    @Resource
    RandomString rs;
    @Resource
    IFriendDao friendDao;
    public User verifyPassword(String username, String password) {
        User user = dao.getUserByUsername(username);
        String md5 = rs.criptPassWord(password, user.getSalt());
        if(md5.equals(user.getPassword())){
            return user;
        }
        else return null;
    }

    public List<Friend> getFriends(String uuid) {
        List<Friend> friends = friendDao.getFriendByUuid(uuid);
        return friends;
    }
}
