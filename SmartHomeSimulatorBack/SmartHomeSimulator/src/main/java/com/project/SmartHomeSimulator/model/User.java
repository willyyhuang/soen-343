package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String homeLocation;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
