package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.*;
import com.project.SmartHomeSimulator.model.Zones.Zone;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObjectType;
import com.project.SmartHomeSimulator.module.SimulationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class SmartHomeHeatingServiceTest {

    @Autowired
    SmartHomeHeatingService smartHomeHeatingService = new SmartHomeHeatingService();

    @Autowired
    SimulationContextService simulationContextService;

    @Autowired
    private UserService userService;

    private SimulationContext simulationContext = SimulationContext.getInstance();

    private void setup() {
        userService = new UserService();
        User user = new User();
        user.setRole(Role.PARENT);
        user.setName("name");
        user.setHomeLocation("outside");
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser("name");

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"bedroom\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"building entrance\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"garage\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}]\"}";
        simulationContextService.loadLayout(homeLayoutFile);
    }

    /**
     * Adding a zone test
     * Use case ID = 8
     */
    @Test
    public void addZoneTest_8() {
        setup();
        Zone zone = new Zone();
        zone.setName("zone");
        List<String> namesRoom = new ArrayList<>();
        namesRoom.add("bedroom");
        namesRoom.add("garage");
        zone.setDesiredTemp(15);
        zone.setPeriod1("10:00");
        zone.setPeriod1Temp(20);
        zone.setRoomsInZone(namesRoom);
        smartHomeHeatingService.setCurrentTemp("garage",15);

        boolean result = smartHomeHeatingService.addZone(zone);
        assertTrue(result);

    }

    /**
     * Override a room temp test
     * Use-Case ID = 10
     */
    @Test
    public void changeRoomTempTest_10() {
        setup();
        addZoneTest_8();
        boolean result = smartHomeHeatingService.changeRoomTemp("bedroom", 25);
        smartHomeHeatingService.setCurrentTemp("bedroom",25);
        assertTrue(result);

        assertTrue(simulationContext.getHomeLayout().getRoomByName("bedroom").isOverridden());
    }

    /**
     * Change a zone temp when a period is reached
     * ID = 11
     * Sub-Function
     */
    @Test
    public void changeZoneTemp_11() {
        setup();
        addZoneTest_8();
        changeRoomTempTest_10();
        boolean result = smartHomeHeatingService.changeZoneTemp("zone", 1);
        assertTrue(result);

        Room bedroom = simulationContext.getHomeLayout().getRoomByName("bedroom");
        RoomObject acBedroom = bedroom.getRoomObjectByType(RoomObjectType.AC);
        RoomObject heaterBedroom = bedroom.getRoomObjectByType(RoomObjectType.HEATER);
        RoomObject heaterGarage = simulationContext.getHomeLayout().getRoomByName("garage").getRoomObjectByType(RoomObjectType.HEATER);

        assertTrue(acBedroom.isStatus());
        assertTrue(heaterGarage.isStatus());
        assertFalse(heaterBedroom.isStatus());
    }

    /**
     * Set temperature threshold
     * ID = 12
     * User-level
     */
    @Test
    public void setThreshold_12() {
        setup();
        simulationContext.setTempThreshold(0);
        double result = simulationContext.getTempThreshold();
        assertEquals(0, result,0);
    }
}