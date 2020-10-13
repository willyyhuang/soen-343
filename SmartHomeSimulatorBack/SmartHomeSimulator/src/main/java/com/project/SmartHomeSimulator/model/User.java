package com.project.SmartHomeSimulator.model;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.swagger2.mappers.ModelMapper;

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
    private String currentSimulationUser;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCurrentSimulationUser(String currentSimulationUser) {
        this.currentSimulationUser = currentSimulationUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
