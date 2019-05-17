package com.chatting.model;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("chattingLog")
public class ChattingLog {
    private int id, is_delivery;
    private String uuid_to;
    private String uuid_from;
    private String message;
    private Date created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_delivery() {
        return is_delivery;
    }

    public void setIs_delivery(int is_delivery) {
        this.is_delivery = is_delivery;
    }

    public String getUuid_to() {
        return uuid_to;
    }

    public void setUuid_to(String uuid_to) {
        this.uuid_to = uuid_to;
    }

    public String getUuid_from() {
        return uuid_from;
    }

    public void setUuid_from(String uuid_from) {
        this.uuid_from = uuid_from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
