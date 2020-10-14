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

    public String getCurrentSimulationProfile() {
        return currentSimulationProfile;
    }

    public void setCurrentSimulationProfile(String currentSimulationProfile) {
        this.currentSimulationProfile = currentSimulationProfile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
