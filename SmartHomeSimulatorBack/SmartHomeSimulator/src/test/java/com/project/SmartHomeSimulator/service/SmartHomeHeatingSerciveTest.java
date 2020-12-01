package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.*;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObjectType;
import com.project.SmartHomeSimulator.module.SimulationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class SmartHomeHeatingSerciveTest {

    @Autowired
    SmartHomeHeatingServices smartHomeHeatingServices = new SmartHomeHeatingServices();

    @Autowired
    SimulationContextService simulationContextService;

    @Autowired
    private UserService userService;

    private static SimulationContext simulationContext = SimulationContext.getInstance();

    private void setup(){
        userService = new UserService();
        User user = new User();
        user.setRole(Role.PARENT);
        user.setName("name");
        user.setHomeLocation("outside");
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser("name");

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"bedroom\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"building entrance\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"garage\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);



    }
    @Test
    public void addZoneTest_(){
        setup();
        Zone zone = new Zone();
        zone.setName("zone");
        List<String> namesRoom = new ArrayList<>();
        namesRoom.add("bedroom");
        namesRoom.add("garage");
        zone.setCurrentTemp(15);
        zone.setPeriod1("10:00");
        zone.setPeriod1Temp(20);
        zone.setRoomsInZone(namesRoom);

        boolean result = smartHomeHeatingServices.addZone(zone);
        assertTrue(result);

    }

    @Test
    public void changeRoomTempTest_(){
        setup();
        addZoneTest_();
        boolean result = smartHomeHeatingServices.changeRoomTemp("bedroom",25);
        assertTrue(result);

        assertTrue(simulationContext.getHomeLayout().getRoomByName("bedroom").isOverridden());
    }

    @Test
    public void changeZoneTemp_(){
        setup();
        addZoneTest_();
        changeRoomTempTest_();
        boolean result = smartHomeHeatingServices.changeZoneTemp("zone",1);
        assertTrue(result);

        Room bedroom = simulationContext.getHomeLayout().getRoomByName("bedroom");
        RoomObject acBedroom = bedroom.getRoomObjectByType(RoomObjectType.AC);
        RoomObject heaterBedroom = bedroom.getRoomObjectByType(RoomObjectType.HEATER);
        RoomObject heaterGarage = simulationContext.getHomeLayout().getRoomByName("garage").getRoomObjectByType(RoomObjectType.HEATER);

        assertTrue(acBedroom.isStatus());
        assertTrue(heaterGarage.isStatus());
        assertFalse(heaterBedroom.isStatus());
    }
}
