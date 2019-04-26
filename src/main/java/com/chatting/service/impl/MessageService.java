package com.chatting.service.impl;

import com.chatting.dao.IFriendDao;
import com.chatting.dao.IMessageDao;
import com.chatting.model.ChattingLog;
import com.chatting.model.Friend;
import com.chatting.service.IMessageService;
import com.chatting.util.ResponseData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("MessageService")
public class MessageService implements IMessageService {
    @Resource
    ProducerService producer;
    @Resource
    IMessageDao dao;
    @Resource
    ResponseData responseData;
    @Resource
    IFriendDao friendDao;
    public boolean sendMessage(String fromUuid, String toUuid, String message) {
        ChattingLog log = new ChattingLog();
        log.setUuid_from(fromUuid);
        log.setUuid_to(toUuid);
        log.setMessage(message);
        log.setCreated_at(new Date());
        dao.insertNewMessage(log);
        if(log.getId()>0) {
            String result = responseData.assembleMessage(log.getId(), message);
            producer.send("message_to_" + toUuid, result);
            return true;
        }else return false;
    }

    public List<Friend> getFriendsAndMessages(String uuid) {
        return friendDao.selectFriendByMyUuid(uuid);
    }

    public List<ChattingLog> getMessage(String fromUuid, String toUuid) {
        return dao.getMessage(fromUuid, toUuid);
    }
}
