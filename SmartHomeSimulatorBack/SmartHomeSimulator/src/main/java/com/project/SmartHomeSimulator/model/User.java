package com.project.SmartHomeSimulator.model;

import java.io.File;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    private int insideTemp;
    private int outsideTemp;
    private String time;
    private String date;

    private File fileLayout;

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



    public void setUsername(String username) {
        this.username = username;
    }

    public int getInsideTemp() { return insideTemp; }

    public int getOutsideTemp() { return outsideTemp; }

    public String getTime() { return time; }

    public String getDate() { return date; }

    public void setInsideTemp(int insideTemp) { this.insideTemp = insideTemp; }

    public void setOutsideTemp(int outsideTemp) { this.outsideTemp = outsideTemp; }

    public void setTime(String time) { this.time = time; }

    public void setDate(String date) { this.date = date; }

    public File getFileLayout() {
      return fileLayout;
    }

    public void setFileLayout(File fileLayout) {
      this.fileLayout = fileLayout;
    }

}
