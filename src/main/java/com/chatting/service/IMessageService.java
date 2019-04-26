package com.chatting.service;

import com.chatting.model.ChattingLog;
import com.chatting.model.Friend;

import java.util.List;

public interface IMessageService {
    boolean sendMessage(String fromUuid, String toUuid, String message);
    List<Friend> getFriendsAndMessages(String uuid);
    List<ChattingLog> getMessage(String fromUuid, String toUuid);
}
