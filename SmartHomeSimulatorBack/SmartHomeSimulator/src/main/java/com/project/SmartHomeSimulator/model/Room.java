package com.project.SmartHomeSimulator.model;

import java.util.Arrays;

public class Room {
	
	private String name;
	private String[] devices;

	public Room() {
		// default constructor
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getDevices() {
		return devices;
	}

	public void setDevices(String[] devices) {
		this.devices = devices;
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", devices=" + Arrays.toString(devices) + "]";
	}

}
