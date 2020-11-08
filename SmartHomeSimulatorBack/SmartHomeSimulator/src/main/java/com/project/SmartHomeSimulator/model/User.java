package com.project.SmartHomeSimulator.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.module.SimulationContext;

/**
 * User class is used to hold information pertaining the users in the simulation
 */
@JsonComponent
@JsonIgnoreProperties
public class User {
	private String name;
	private Role role;
	private String homeLocation;

	public String getName() {
		return name;
	}
	
	public User() {
		
	}

	public User(String name, Role role, String homeLocation) {
		this.name = name;
		this.role = role;
		this.homeLocation = homeLocation;
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

	public String getHomeLocation() {
		return homeLocation;
	}

	public void setHomeLocation(String homeLocation) {
		this.homeLocation = homeLocation;
	}

	@Override
	public String toString() {
		return "{\"name\": " + '"' + name + "\", " + "\"role\": " + '"' + role + "\", "
				+ "\"homeLocation\": " + '"' + homeLocation + '"' + "}";
	}
	
}
