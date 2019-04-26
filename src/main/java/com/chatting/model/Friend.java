package com.chatting.model;

import org.apache.ibatis.type.Alias;

import java.util.List;


@Alias("friend")
public class Friend {
    private int id;
    private String uuid_first, uuid_second;
    private List<ChattingLog> chattingLogs;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ChattingLog> getChattingLogs() {
        return chattingLogs;
    }

    public void setChattingLogs(List<ChattingLog> chattingLogs) {
        this.chattingLogs = chattingLogs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid_first() {
        return uuid_first;
    }

    public void setUuid_first(String uuid_first) {
        this.uuid_first = uuid_first;
    }

    public String getUuid_second() {
        return uuid_second;
    }

    public void setUuid_second(String uuid_second) {
        this.uuid_second = uuid_second;
    }
}
