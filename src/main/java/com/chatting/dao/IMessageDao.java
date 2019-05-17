package com.chatting.dao;

import com.chatting.model.ChattingLog;
import com.chatting.model.HistoryMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IMessageDao {
    int insertNewMessage(ChattingLog log);

    List<HistoryMessage> getMessageByToUuid(String uuid);

    List<ChattingLog> getMessage(@Param("fromUuid") String fromUuid, @Param("toUuid") String toUuid);

    int updateIsDelivery(@Param("myUuid") String myUuid, @Param("friendUuid") String friendUuid);
}
