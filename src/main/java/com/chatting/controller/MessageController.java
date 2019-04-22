package com.chatting.controller;

import com.chatting.service.IMessageService;
import com.chatting.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/message")
@ResponseBody
public class MessageController {
    @Resource
    IMessageService service;
    @Resource
    ResponseData responseData;
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendMessage(@RequestParam String toUuid, @RequestParam String message){
        try {
            service.sendMessage("123", toUuid, message);
            return responseData.successed(null);
        }catch (Exception e){
            return responseData.unKnowError();
        }
    }
}
