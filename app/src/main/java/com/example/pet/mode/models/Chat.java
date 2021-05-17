package com.example.pet.mode.models;

import java.io.Serializable;

public class Chat implements Serializable {
    String  image  ;
    String name , lastMessage  ;

    public Chat(String image, String name, String lastMessage) {
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
