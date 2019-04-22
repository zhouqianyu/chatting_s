package com.chatting.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ResponseData {
    public String successed(String data){
        return assemble(200,"success", data);
    }
    public String failed(String data){
        return assemble(403,"Forbidden", data);
    }
    public String unauthorized(String data){
        return assemble(402,"fail", data);
    }
    public String unKnowError(){
        return assemble(500,"unknown error",null);
    }
    public String assemble(int code, String msg, Object data) {
        CallBackCriteria callBackCriteria = new CallBackCriteria(code, msg, data);
        return parse(callBackCriteria);
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


}
