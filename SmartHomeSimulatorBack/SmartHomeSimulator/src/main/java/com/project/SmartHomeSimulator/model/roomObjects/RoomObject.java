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
    private RoomObjectType roomObjectType;
    private boolean status;

    public RoomObject(){
        this.id = UUID.randomUUID();
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomObject{" +
                "id=" + id +
                ", roomObjectType=" + roomObjectType +
                ", status=" + status +
                '}';
    }
}
