package com.project.SmartHomeSimulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private String currentSimulationProfile;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCurrentSimulationProfile(String currentSimulationUser) {
        this.currentSimulationProfile = currentSimulationUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
