package com.example.pet.mode.models;

public class Item {
     int avatar  ;
    String name , status , time ;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Item(int avatar, String name, String status, String time) {
        this.avatar = avatar;
        this.name = name;
        this.status = status;
        this.time = time;
    }
}
