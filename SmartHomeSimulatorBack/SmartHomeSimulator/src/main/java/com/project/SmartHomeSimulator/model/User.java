package com.project.SmartHomeSimulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotNull(message = "Role cannot be blank")
    @Enumerated(EnumType.STRING)
    private Role role;

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
