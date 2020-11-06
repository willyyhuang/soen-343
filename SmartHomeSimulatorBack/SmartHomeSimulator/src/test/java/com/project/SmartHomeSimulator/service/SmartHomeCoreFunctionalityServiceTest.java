package com.project.SmartHomeSimulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.module.SimulationContext;
import net.minidev.json.JSONObject;
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
    private static SimulationContext simulationContext = SimulationContext.getInstance();
    private User user;


    /**
     * choosing to block or unblock a window test
     */
    @Test
    public void blockUnblockWindow() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "bedroom");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        System.out.println(simulationContext);
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject window = room.getObjects().get(0);

        //unblock
        boolean result = smartHomeCoreFunctionalityService.openCloseWindow(room.getName(),window.getId().toString(),false);
        assertEquals(result,true);

        //block
        result = smartHomeCoreFunctionalityService.openCloseWindow(room.getName(),window.getId().toString(),true);
        assertEquals(result,true);
    }

    /**
     * choosing to turn off or on a light test
     */
    @Test
    public void onOffLights() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "bedroom");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        System.out.println(simulationContext);
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"LIGHT\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject light = room.getObjects().get(0);

        //on
        boolean result = smartHomeCoreFunctionalityService.onOffLights(room.getName(),light.getId().toString(),true);
        assertEquals(result,true);

        //off
        result = smartHomeCoreFunctionalityService.onOffLights(room.getName(),light.getId().toString(),false);
        assertEquals(result,true);
    }


}
