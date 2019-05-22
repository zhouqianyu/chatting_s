package com.chatting.controller;

import com.baidu.aip.speech.AipSpeech;
import com.chatting.dao.IMessageDao;
import com.chatting.model.ChattingLog;
import com.chatting.model.HistoryMessage;
import com.chatting.service.IMessageService;
import com.chatting.util.ResponseData;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
@ResponseBody
public class MessageController {
    @Resource
    IMessageService service;
    @Resource
    ResponseData responseData;
    @Resource
    IMessageDao dao;
    @Resource
    AipSpeech speech;
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

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public String read(@RequestParam String uuid, HttpServletRequest request){
        String my_uuid = (String) request.getAttribute("uuid");
        if(dao.updateIsDelivery(my_uuid, uuid) > 0){
            return responseData.successed("");
        }else
            return responseData.unKnowError();
    }
    @RequestMapping(value = "/recognize", method = RequestMethod.POST)
    public String recognize(@RequestParam("file") CommonsMultipartFile file){
        String path = "/var/www/record/"+new Date().getTime()+file.getOriginalFilename();
        File newFile = new File(path);
        try {
            file.transferTo(newFile);
            RestTemplate template = new RestTemplate();
            FileSystemResource resource = new FileSystemResource(newFile);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("file", resource);
            String string = template.postForObject("http://39.105.57.231/recognize", param, String.class);
            return responseData.successed(string);
        }catch (Exception e){
            return responseData.unKnowError();
        }finally {
            newFile.delete();
        }
    }
    @RequestMapping(value = "/recognize/api", method = RequestMethod.POST)
    public String recognizeApi(@RequestParam("file") CommonsMultipartFile file) throws IOException {
        String path = "/var/www/record/"+new Date().getTime()+file.getOriginalFilename();
        File newFile = new File(path);
        file.transferTo(newFile);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dev_pid", 1537);
        JSONObject res = speech.asr(path, "wav", 16000, map);
        try {
            if (res.getInt("err_no") == 0) {
                return responseData.successed(res.getJSONArray("result").get(0).toString());
            } else {
                return responseData.failed(res.getInt("err_no") + "");
            }
        }catch (Exception e){
            return responseData.unKnowError();
        }finally {
            newFile.delete();
        }
    }
}
