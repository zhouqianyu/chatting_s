package com.chatting.model;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("user")
public class User {
    private String uuid;
    private String username;
    private String password;
    private String salt;
    List<Friend> friends;
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId() {
        return uuid;
    }

    public void setId(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
