package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@JsonComponent
@Getter
@Setter
public class HomeLayout {
    private List<Room> roomList;

    public boolean readHomeLayout(String homeLayout) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(homeLayout);
        try {
            this.roomList = objectMapper.readValue(homeLayout, HomeLayout.class).getRoomList();
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Room getRoomByName(String name) {
        for (Room room : this.roomList) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }
}
