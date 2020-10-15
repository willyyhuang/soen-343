package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@JsonComponent
@JsonIgnoreProperties
public class User {
    private String name;
    private Role role;
    private Room homeLocation;
}
