package com.project.SmartHomeSimulator.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.Id;
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

    private String homeLocation;
    
    private File fileLayout;

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

	public File getFileLayout() {
		return fileLayout;
	}

	public void setFileLayout(File fileLayout) {
		this.fileLayout = fileLayout;
	}
    
}
