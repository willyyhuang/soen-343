package com.project.SmartHomeSimulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.module.SimulationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SmartHomeCoreFunctionalityServiceTest {

    @Autowired
    SimulationContextService simulationContextService;

    @Autowired
    SmartHomeCoreFunctionalityService smartHomeCoreFunctionalityService;

    @Autowired
    private UserService userService;
    private SimulationContext simulationContext = SimulationContext.getInstance();

    private void setup() {
        userService = new UserService();
        User user = new User();
        user.setRole(Role.PARENT);
        user.setName("testUser");
        user.setHomeLocation("outside");
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser("testUser");

    }

    /**
     * choosing to block or unblock a window test
     * Use case ID = 0
     */
    @Test
    public void blockUnblockWindow_0() throws JsonProcessingException {
        setup();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject window = room.getObjects().get(0);

        //unblock
        boolean result = smartHomeCoreFunctionalityService.blockUnblockWindow(room.getName(), window.getId().toString(), false);
        assertEquals(true, result);

        //block
        result = smartHomeCoreFunctionalityService.blockUnblockWindow(room.getName(), window.getId().toString(), true);
        assertEquals(true, result);
    }

    /**
     * choosing to open or close a window test
     * Use case ID = 1
     */
    @Test
    public void openCloseWindow_1() throws JsonProcessingException {
        setup();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject window = room.getObjects().get(0);

        //unblock
        boolean result = smartHomeCoreFunctionalityService.openCloseWindow(room.getName(), window.getId().toString(), false);
        assertEquals(true, result);

        //block
        result = smartHomeCoreFunctionalityService.openCloseWindow(room.getName(), window.getId().toString(), true);
        assertEquals(true, result);
    }

    /**
     * choosing to open or close a door test
     * Use case ID = 1
     */
    @Test
    public void openCloseDoor_1() throws JsonProcessingException {
        setup();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"DOOR\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject door = room.getObjects().get(0);

        //unblock
        boolean result = smartHomeCoreFunctionalityService.openCloseDoors(room.getName(), door.getId().toString(), false);
        assertEquals(true, result);

        //block
        result = smartHomeCoreFunctionalityService.openCloseDoors(room.getName(), door.getId().toString(), true);
        assertEquals(true, result);
    }

    /**
     * choosing to turn off or on a light test
     * Use case ID = 1
     */
    @Test
    public void onOffLights_1() throws JsonProcessingException {
        setup();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"LIGHT\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject light = room.getObjects().get(0);

        //on
        boolean result = smartHomeCoreFunctionalityService.onOffLights(room.getName(), light.getId().toString(), true);
        assertEquals(true, result);

        //off
        result = smartHomeCoreFunctionalityService.onOffLights(room.getName(), light.getId().toString(), false);
        assertEquals(true, result);
    }


    /**
     * Set lights to automode on
     * Use case ID = 2
     */
    @Test
    public void automodeLights_2() throws JsonProcessingException {
        setup();

        System.out.println(simulationContext);
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"LIGHT\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject light = room.getObjects().get(0);

        simulationContextService.setAutoMode(true);
        userService.editHomeLocation(simulationContext.getCurrentSimulationUser().getName(), "string");
        assertEquals(true, light.isStatus());

    }

    /**
     * verify permissions
     * Use case ID = 5
     */
    @Test
    public void permissionCheck_5() throws JsonProcessingException {
        setup();
        simulationContext.getCurrentSimulationUser().setRole(Role.CHILD);

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject door = room.getObjects().get(0);
        RoomObject window = room.getObjects().get(1);


        boolean result = smartHomeCoreFunctionalityService.openCloseDoors(room.getName(), door.getId().toString(), true);
        assertEquals(false, result);


        result = smartHomeCoreFunctionalityService.onOffLights(room.getName(), window.getId().toString(), false);
        assertEquals(true, result);
    }
}
