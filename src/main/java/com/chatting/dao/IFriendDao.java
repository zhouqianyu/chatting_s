package com.chatting.dao;

import com.chatting.model.Friend;

import java.util.List;

public interface IFriendDao {
    List<Friend> selectFriendByMyUuid(String uuid);
}
