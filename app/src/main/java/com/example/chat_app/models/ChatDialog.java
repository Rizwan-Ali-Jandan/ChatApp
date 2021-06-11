package com.example.chat_app.models;

import com.google.firebase.firestore.Exclude;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.ArrayList;

public class ChatDialog implements IDialog {

    public String id;
    public String dialogPhoto;
    public String dialogName;
    @Exclude
    public ArrayList<User> users = new ArrayList<>();
    public Message lastMessage;
    public int unreadCount;

    public ChatDialog(Message firstMessage){
        this.dialogPhoto = " ";
        this.dialogName= firstMessage.text;
        this.users.add(firstMessage.user);
        this.lastMessage=firstMessage;
        this.unreadCount=0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(IMessage message) {

        this.lastMessage = (Message) message;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }
}

