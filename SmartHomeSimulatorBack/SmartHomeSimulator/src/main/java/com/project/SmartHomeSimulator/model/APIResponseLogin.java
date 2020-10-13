package com.project.SmartHomeSimulator.model;

public class APIResponseLogin {

    public boolean success;
    public String username;

    public APIResponseLogin(){

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
