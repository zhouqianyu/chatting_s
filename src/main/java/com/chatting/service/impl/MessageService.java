package com.chatting.service.impl;

import com.chatting.dao.IFriendDao;
import com.chatting.dao.IMessageDao;
import com.chatting.dao.IUserDao;
import com.chatting.model.ChattingLog;
import com.chatting.model.Friend;
import com.chatting.model.HistoryMessage;
import com.chatting.model.User;
import com.chatting.service.IMessageService;
import com.chatting.util.ResponseData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("MessageService")
@Transactional
public class MessageService implements IMessageService {
    @Resource
    ProducerService producer;
    @Resource
    IMessageDao dao;
    @Resource
    ResponseData responseData;
    @Resource
    IFriendDao friendDao;
    @Resource
    IUserDao userDao;
    public boolean sendMessage(String fromUuid, String toUuid, String message) {
        ChattingLog log = new ChattingLog();
        log.setUuid_from(fromUuid);
        log.setUuid_to(toUuid);
        log.setMessage(message);
        log.setCreated_at(new Date());
        dao.insertNewMessage(log);
        User user = userDao.getUserByUuid(toUuid);
        if(log.getId()>0) {
            String result = responseData.assembleMessage(log, user);
            producer.send("message_to_" + toUuid, result);
            return true;
        }else return false;
    }

    public List<HistoryMessage> getFriendsAndMessages(String uuid) {
        return friendDao.selectFriendByMyUuid(uuid);
    }
    public List<ChattingLog> getMessage(String fromUuid, String toUuid) {
        dao.updateIsDelivery(toUuid, fromUuid);
        return dao.getMessage(fromUuid, toUuid);
    }
}
