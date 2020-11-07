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
    private SimulationContext simulationContext = SimulationContext.getInstance();


    /**
     * choosing to block or unblock a window test
     * Use case ID = 0
     */
    @Test
    public void blocckunblockWindow_0() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject window = room.getObjects().get(0);

        //unblock
        boolean result = smartHomeCoreFunctionalityService.blockUnblockWindow(room.getName(),window.getId().toString(),false);
        assertEquals(result,true);

        //block
        result = smartHomeCoreFunctionalityService.blockUnblockWindow(room.getName(),window.getId().toString(),true);
        assertEquals(result,true);
    }

    /**
     * choosing to open or close a window test
     * Use case ID = 1
     */
    @Test
    public void opencloseWindow_1() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

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
     * choosing to open or close a door test
     * Use case ID = 2
     */
    @Test
    public void opencloseDoor_2() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"DOOR\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject door = room.getObjects().get(0);

        //unblock
        boolean result = smartHomeCoreFunctionalityService.openCloseDoors(room.getName(),door.getId().toString(),false);
        assertEquals(result,true);

        //block
        result = smartHomeCoreFunctionalityService.openCloseDoors(room.getName(),door.getId().toString(),true);
        assertEquals(result,true);
    }

    /**
     * choosing to turn off or on a light test
     * Use case ID = 3
     */
    @Test
    public void onOffLights_3() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

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


    /**
     * Set lights to automode on
     * Use case ID = 4
     */
    @Test
    public void automodeLights_4() throws JsonProcessingException {

        System.out.println(simulationContext);
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"LIGHT\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject light = room.getObjects().get(0);

        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        simulationContextService.setAutoMode(true);
        userService.editHomeLocation(user.getName(),"string");
        assertEquals(light.isStatus(),true);

    }

    /**
     * verify permissions
     * Use case ID = 5
     */
    @Test
    public void pemrissionCheck_5() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "CHILD");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject door = room.getObjects().get(0);
        RoomObject window = room.getObjects().get(1);

        //on
        boolean result = smartHomeCoreFunctionalityService.openCloseDoors(room.getName(),door.getId().toString(),true);
        assertEquals(result,false);

        //off
        result = smartHomeCoreFunctionalityService.onOffLights(room.getName(),window.getId().toString(),false);
        assertEquals(result,false);
    }
}
