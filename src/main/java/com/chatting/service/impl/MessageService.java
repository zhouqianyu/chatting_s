package com.chatting.service.impl;

import com.chatting.dao.IMessageDao;
import com.chatting.model.ChattingLog;
import com.chatting.service.IMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("MessageService")
public class MessageService implements IMessageService {
    @Resource
    ProducerService producer;
    @Resource
    IMessageDao dao;
    public boolean sendMessage(String fromUuid, String toUuid, String message) {
        ChattingLog log = new ChattingLog();
        log.setUuid_from(fromUuid);
        log.setUuid_to(toUuid);
        log.setMessage(message);
        log.setCreated_at(new Date());
        dao.insertNewMessage(log);
        producer.send("message_to"+toUuid, message);
        return true;
    }
}
