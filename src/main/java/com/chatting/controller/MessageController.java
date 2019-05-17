package com.chatting.controller;

import com.chatting.model.ChattingLog;
import com.chatting.model.HistoryMessage;
import com.chatting.service.IMessageService;
import com.chatting.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/message")
@ResponseBody
public class MessageController {
    @Resource
    IMessageService service;
    @Resource
    ResponseData responseData;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendMessage(@RequestParam String toUuid,
                              @RequestParam String message, HttpServletRequest request) {
        try {
            String fromUuid = (String) request.getAttribute("uuid");
            if (service.sendMessage(fromUuid, toUuid, message))
                return responseData.successed(null);
            else return responseData.unKnowError();
        } catch (Exception e) {
            return responseData.unKnowError();
        }
    }

    @RequestMapping(value = "/friends", method = RequestMethod.POST)
    public String getFriendsAndMessages(HttpServletRequest request) {
        String uuid = (String) request.getAttribute("uuid");
        List<HistoryMessage> results = service.getFriendsAndMessages(uuid);
        return responseData.assembleCallBack(200, "success", results);
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    public String getMessageByUUid(@RequestParam String uuid, HttpServletRequest request) {
        String my_uuid = (String) request.getAttribute("uuid");
        List<ChattingLog> results = service.getMessage(uuid, my_uuid);
        return responseData.assembleCallBack(200, "success", results);
    }
}
