package com.chatting.controller;


import com.chatting.model.Friend;
import com.chatting.model.User;
import com.chatting.service.IUserService;
import com.chatting.util.Cache;
import com.chatting.util.RandomString;
import com.chatting.util.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Resource
    IUserService userService;
    @Resource
    JmsTemplate jmsTemplate;
    @Resource
    Cache cache;
    @Resource
    ResponseData data;
    @Resource
    RandomString randomString;

    @RequestMapping(value = "/friends", method = RequestMethod.POST)
    public String getFriends(HttpServletRequest request) {
        String uuid = (String) request.getAttribute("uuid");
        List<Friend> friends = userService.getFriends(uuid);
        return data.assembleCallBack(200, "success", friends);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String getMyInfo(HttpServletRequest request) {
        String uuid = (String) request.getAttribute("uuid");
        User user = userService.getMyInfo(uuid);
        return data.assembleCallBack(200, "success", user);
    }

    @RequestMapping(value = "changeInfo", method = RequestMethod.POST)
    public String changeInfo(String describe, String oldpassword, String newPassword, HttpServletRequest request) {
        String uuid = (String) request.getAttribute("uuid");
        String username = (String) request.getAttribute("username");
        User user = new User();
        user.setUuid(uuid);
        if (oldpassword != null) {
            user = userService.verifyPassword(username, oldpassword);
            if (user != null) {
                String md5 = randomString.criptPassWord(newPassword, user.getSalt());
                user.setPassword(md5);
            }
        }
        if (user != null && describe != null) {
            user.setDescribe(describe);

        }
        if (user != null) {
            if (userService.changeInfo(user) == 1) {
                return data.successed("");
            }
        }
        return data.failed("");
    }
}
