package com.example.pet.mode.models;

public class Message {
    private String sender , receiver , message , status;
    public Message(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message(String sender, String receiver, String message , String status) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.status = status;

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
