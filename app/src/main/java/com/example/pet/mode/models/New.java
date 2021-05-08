package com.example.pet.mode.models;

import java.util.ArrayList;

public class New {
    private String id;
    private String content;
    private int likes;
    private String user_id;
    private ArrayList<Image> images;
    private String tag;


    public New(
            String id, String content, int likes, String user_id, ArrayList<Image> images, String tag
    ) {
        this.id = id;
        this.content = content;
        this.likes = likes;
        this.user_id = user_id;
        this.images = images;
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
