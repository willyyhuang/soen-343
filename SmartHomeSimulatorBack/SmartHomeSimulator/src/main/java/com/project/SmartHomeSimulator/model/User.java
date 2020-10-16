package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.jackson.JsonComponent;


@JsonComponent
@JsonIgnoreProperties
public class User {
    private String name;
    private Role role;
    private Room homeLocation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Room getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Room homeLocation) {
        this.homeLocation = homeLocation;
    }
}
