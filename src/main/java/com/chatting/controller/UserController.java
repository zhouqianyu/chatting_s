package com.chatting.controller;


import com.chatting.service.UserService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.*;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Resource
    UserService userService;
    @Resource
    JmsTemplate jmsTemplate;
    @Resource
    DefaultMessageListenerContainer container;
    @Resource
    MessageListener listener;
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String select(@RequestParam String username){
        return userService.getPasswordByUserName(username);
    }
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    public String connection(@RequestParam String username){
        container.setDestinationName(username);
        container.start();
        return "123";
    }
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(@RequestParam String username, @RequestParam final String message){
        jmsTemplate.send(username, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
        return "123";
    }

    @RequestMapping(value = "con2", method = RequestMethod.GET)
    public String con2(@RequestParam String username){

        return "123";
    }
}
