package com.chatting.dao;

import com.chatting.model.ChattingLog;

public interface IMessageDao {
    int insertNewMessage(ChattingLog log);
}
