package com.chatting.util;

import com.chatting.model.ChattingLog;
import com.chatting.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ResponseData {
    public String successed(String data){
        return assembleCallBack(200,"success", data);
    }
    public String failed(String data){
        return assembleCallBack(403,"Forbidden", data);
    }
    public String unauthorized(String data){
        return assembleCallBack(402,"fail", data);
    }
    public String unKnowError(){
        return assembleCallBack(500,"unknown error",null);
    }
    public String assembleCallBack(int code, String msg, Object data) {
        CallBackCriteria callBackCriteria = new CallBackCriteria(code, msg, data);
        return parse(callBackCriteria);
    }
    public String assembleMessage(ChattingLog log, User user){
        MessageCriteria criteria = new MessageCriteria(log, user);
        return parse(criteria);
    }

    private String parse(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class CallBackCriteria {
        int code;
        String msg;
        Object data;

        private CallBackCriteria(int code, String msg, Object data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public Object getData() {
            return data;
        }
    }
    private class MessageCriteria{
        ChattingLog log;
        User user;

        public MessageCriteria(ChattingLog log, User user) {
            this.log = log;
            this.user = user;
        }
    }

}
