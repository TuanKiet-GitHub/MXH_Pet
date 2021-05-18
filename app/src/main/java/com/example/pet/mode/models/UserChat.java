package com.example.pet.mode.models;

import java.io.Serializable;

public class UserChat implements Serializable {
    String tokenSender;
    String id ;
    String  image  ;
    String name , lastMessage  ;

    public String getTokenSender() {
        return tokenSender;
    }

    public void setTokenSender(String tokenSender) {
        this.tokenSender = tokenSender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserChat(String tokenSender, String id , String image, String name, String lastMessage) {
        this.tokenSender = tokenSender;
        this.id = id;
        this.image = image;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


}
