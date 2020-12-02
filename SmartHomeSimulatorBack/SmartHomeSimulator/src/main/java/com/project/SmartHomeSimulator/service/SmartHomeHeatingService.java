package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.Zone;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeHeatingProxy;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class SmartHomeHeatingService {
    private static SimulationContext simulationContext = SimulationContext.getInstance();
    private SmartHomeHeatingProxy smartHomeHeatingProxy = SmartHomeHeatingProxy.getInstance();

    /**
     * Add a zone
     *
     * @param zone
     * @return
     */
    public boolean addZone(Zone zone) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeHeatingProxy.addZone(user, zone);
    }

    /**
     * change temperature of a room, override
     *
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeRoomTemp(String roomName, int newTemp) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeHeatingProxy.changeRoomTemp(user, roomName, newTemp);
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     *
     * @param zoneName
     * @param period
     * @return
     */
    public boolean changeZoneTemp(String zoneName, int period) {
        if (period != 0) {
            ArrayList<Room> rooms = simulationContext.getHomeLayout().getRoomsInZone(zoneName);
            return smartHomeHeatingProxy.changeZoneTemp(zoneName, rooms, period);
        }
        return true;
    }

    /**
     * Open or close window
     *
     * @param windowId
     * @param state
     * @return
     */
    public boolean openCloseWindow(String windowId, boolean state) {
        return smartHomeHeatingProxy.openCloseWindows(windowId, state);
    }

    /**
     * set season
     */
    public void setSeason(boolean isSummer) {
        smartHomeHeatingProxy.setSeason(isSummer);
    }
}
