package com.project.SmartHomeSimulator.model.roomObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonComponent;

import java.util.UUID;

@JsonComponent
public class Object {
    @JsonProperty(required = false)
    private UUID id;
    private ObjectType objectType;
    private boolean is_On_Open;

    public Object(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public boolean isIs_On_Open() {
        return is_On_Open;
    }

    public void setIs_On_Open(boolean is_On_Open) {
        this.is_On_Open = is_On_Open;
    }
}
