package com.chatting.controller;


import com.chatting.service.IUserService;
import com.chatting.util.Cache;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
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
    IUserService IUserService;
    @Resource
    JmsTemplate jmsTemplate;
    @Resource
    Cache cache;
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    public String connection(@RequestParam String username){
        boolean result = cache.set("username",username);
        return result+"";
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

    @RequestMapping(value = "con2", method = RequestMethod.POST)
    public String con2(@RequestParam String username){

        return "123";
    }
}
