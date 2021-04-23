package com.example.pet.mode.models;

public class Model {
    int image ;
    String title ;
    String describe ;

    public Model(int image, String title, String describe) {
        this.image = image;
        this.title = title;
        this.describe = describe;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
