package com.chatting.service;

public interface IProducerService {
    boolean send(String destination, String message);
}
