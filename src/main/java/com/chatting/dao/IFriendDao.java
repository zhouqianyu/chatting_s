package com.chatting.dao;

import com.chatting.model.Friend;
import com.chatting.model.HistoryMessage;

import java.util.List;

public interface IFriendDao {
    List<HistoryMessage> selectFriendByMyUuid(String uuid);

    List<Friend> getFriendByUuid(String uuid);
}
