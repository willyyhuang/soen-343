package com.project.SmartHomeSimulator.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HouseLayout {

	private Room[] rooms;
	
	public HouseLayout(File jsonFile) {
		jsonParse(jsonFile);
	}
	
	public void jsonParse(File jsonFile) {
		ObjectMapper objectMapper = new ObjectMapper();			
		
		try {
			rooms = objectMapper.readValue(jsonFile, Room[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public Room[] getRooms() {
		return rooms;
	}

	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(rooms);
	}

}

