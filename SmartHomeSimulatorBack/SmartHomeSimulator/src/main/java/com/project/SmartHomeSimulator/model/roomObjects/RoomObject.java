package com.project.SmartHomeSimulator.model.roomObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

import java.util.UUID;

/**
 * All the object in the room will inherit this class
 */
@JsonComponent
public class RoomObject {
    @JsonProperty(required = false)
    private UUID id;
    @JsonProperty(required = false)
    private String name;
    private RoomObjectType roomObjectType;

    public RoomObject(){
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoomObjectType getObjectType() {
        return roomObjectType;
    }

    public void setObjectType(RoomObjectType roomObjectType) {
        this.roomObjectType = roomObjectType;
    }

    @Override
    public String toString() {
        return "RoomObject{" +
                "name=" + name +
                ", id=" + id +
                ", roomObjectType=" + roomObjectType +
                '}';
    }
}
