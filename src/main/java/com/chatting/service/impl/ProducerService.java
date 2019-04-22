package com.chatting.service.impl;

import com.chatting.service.IProducerService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class ProducerService implements IProducerService {
    @Resource
    JmsTemplate jmsTemplate;
    public boolean send(String destination, final String message){
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    session.createTextMessage(message);
                    return null;
                }
            });
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
