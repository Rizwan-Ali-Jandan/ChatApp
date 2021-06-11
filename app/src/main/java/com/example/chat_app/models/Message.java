package com.example.chat_app.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message implements IMessage {

    String id;
    String text;
    User user;
    Date createdAt;

    public Message(String firstMessage, User user) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }


    public Map<String, Object> hashMap() {
        Map<String, Object> hashmap = new HashMap<>();
        hashmap.put("text", text);
        hashmap.put("user", user);
        hashmap.put("createdAt", createdAt);

        return hashmap;
    }
}