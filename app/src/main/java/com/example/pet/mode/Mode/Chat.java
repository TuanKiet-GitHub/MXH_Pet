package com.example.pet.mode.Mode;

public class Chat {
    int image , status ;
    String name , chat  ;

    public Chat(int image, String name, String chat , int status) {
        this.image = image;
        this.name = name;
        this.chat = chat;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
