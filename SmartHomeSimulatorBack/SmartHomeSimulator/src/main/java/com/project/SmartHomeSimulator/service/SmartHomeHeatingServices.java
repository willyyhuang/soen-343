package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.Zone;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObjectType;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeHeatingProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class SmartHomeHeatingServices {
    private static SimulationContext simulationContext = SimulationContext.getInstance();
    private SmartHomeHeatingProxy smartHomeHeatingProxy = SmartHomeHeatingProxy.getInstance();

    /**
     * Add a zone
     * @param zone
     * @return
     */
    public boolean addZone(Zone zone) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeHeatingProxy.addZone(user, zone);
    }

    /**
     * change temperature of a room, override
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeRoomTemp(String roomName, int newTemp){
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeHeatingProxy.changeRoomTemp(user, roomName, newTemp);
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     * @param zoneName
     * @param period
     * @return
     */
    public boolean changeZoneTemp(String zoneName, int period){
        if (period != 0) {
            ArrayList<Room> rooms = simulationContext.getHomeLayout().getRoomsInZone(zoneName);
            return smartHomeHeatingProxy.changeZoneTemp(zoneName, rooms, period);
        }
        return true;
    }

    /**
     * Change AC status when temperature is reached
     * @param roomName
     * @param status
     * @return
     */
    public boolean changeACStatus(String roomName, boolean status ){
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        if (room != null){
            room.getRoomObjectByType(RoomObjectType.AC).setStatus(status);
            return true;
        }
        return false;
    }

    /**
     * change heater status when temperature is reached
     * @param roomName
     * @param status
     * @return
     */
    public boolean changeHeaterStatus(String roomName, boolean status ){
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        if (room != null){
            room.getRoomObjectByType(RoomObjectType.HEATER).setStatus(status);
            return true;
        }
        return false;
    }
}