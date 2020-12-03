package com.project.SmartHomeSimulator.model.Zones;

import java.util.ArrayList;
import java.util.List;

public class ZoneObject {
    private String zoneName;
    private List<String> rooms = new ArrayList<>();

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
}
