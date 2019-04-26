package com.chatting.dao;

import com.chatting.model.ChattingLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IMessageDao {
    int insertNewMessage(ChattingLog log);
    List<ChattingLog> getMessageByToUuid(String uuid);
    List<ChattingLog> getMessage(@Param("fromUuid") String fromUuid, @Param("toUuid") String toUuid);
}
