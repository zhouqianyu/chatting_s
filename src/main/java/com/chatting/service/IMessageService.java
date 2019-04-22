package com.chatting.service;

public interface IMessageService {
    boolean sendMessage(String fromUuid, String toUuid, String message);
}
