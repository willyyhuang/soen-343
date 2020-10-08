package com.project.SmartHomeSimulator.model;

public class APIResponseLogin {

    public boolean success;
    public User user;

    public APIResponseLogin(){

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
