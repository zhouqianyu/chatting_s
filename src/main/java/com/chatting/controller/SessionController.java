package com.chatting.controller;

import com.chatting.model.LoginCallBack;
import com.chatting.model.User;
import com.chatting.service.IUserService;
import com.chatting.util.ResponseData;
import com.chatting.util.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
public class SessionController {
    @Resource
    IUserService userService;
    @Resource
    Token token;
    @Resource
    ResponseData responseData;

    @RequestMapping(value = "/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userService.verifyPassword(username, password);
        if (user != null) {
            token.put("username", user.getUsername()).put("uuid", user.getUuid());
            String result = token.build();
            String img_url = user.getImg_url();
            LoginCallBack callBack = new LoginCallBack();
            callBack.setToken(result);
            callBack.setImg_url(img_url);
            callBack.setUuid(user.getUuid());
            return responseData.assembleCallBack(200, "success", callBack);
        } else {
            return responseData.failed("username or password wrong");
        }
    }
}
