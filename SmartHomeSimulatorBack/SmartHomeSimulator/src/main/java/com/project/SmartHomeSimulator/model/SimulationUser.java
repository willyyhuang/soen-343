package com.project.SmartHomeSimulator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
public class SimulationUser {

    @Id
    private String username;

    @NotBlank(message = "Role cannot be blank")
    private Role role;

    @NotBlank(message = "Home location cannot be blank")
    private String homeLocation;

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getUsername() {
        return username;
    }
}
