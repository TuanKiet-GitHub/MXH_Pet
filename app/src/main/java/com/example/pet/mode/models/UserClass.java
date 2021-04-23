package com.example.pet.mode.models;

public class UserClass {
     String username , password  , email ;

    public String getSdt() {
        return email;
    }

    public void setSdt(String sdt) {
        this.email = email;
    }
    public UserClass(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public UserClass(String username, String password , String email) {
        this.username = username;
        this.password = password;
        this.email = email ;
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
