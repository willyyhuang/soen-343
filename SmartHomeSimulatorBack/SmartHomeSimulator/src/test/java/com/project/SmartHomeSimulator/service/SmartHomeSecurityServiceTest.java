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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SmartHomeSecurityServiceTest {


    @Autowired
    SimulationContextService simulationContextService;

    @Autowired
    SmartHomeCoreFunctionalityService smartHomeCoreFunctionalityService;

    @Autowired
    SmartHomeSecurityService smartHomeSecurityService;

    @Autowired
    private UserService userService;

    private static SimulationContext simulationContext = SimulationContext.getInstance();

    /**
     * set away mode
     * Use case ID = 7
     */
    @Test
    public void awayMode_7() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());
        simulationContextService.startSimulation();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"backyard\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}, {\"name\":\"building entrance\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}, {\"name\":\"garage\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject door = room.getObjects().get(0);
        RoomObject window = room.getObjects().get(1);

        //on
        smartHomeCoreFunctionalityService.openCloseDoors(room.getName(),door.getId().toString(),true);
        smartHomeCoreFunctionalityService.openCloseWindow(room.getName(),window.getId().toString(),true);

        List<User> users= simulationContext.getSimulationUsers();
        for(User userInHome : users){
            userService.editHomeLocation(userInHome.getName(), "outside");
        }

        boolean result = smartHomeSecurityService.setAwayMode(true);
        assertEquals(result,true);

        assertEquals(window.isStatus(), false);
        assertEquals(door.isStatus(), false);
    }

    /**
     * Time before calling authorities set test
     * Use case ID = 4
     */
    @Test
    public void setTimeBeforeAuthorities_4() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "outside");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser(user.getName());

        boolean result = smartHomeSecurityService.setTimeBeforeAuthorities(12);
        assertEquals(result, true);
    }

    /**
     * light and time interval set for away mode
     * Use case ID = 4
     */
    @Test
    public void setTimeInterval_4() throws JsonProcessingException {
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

        List<String> lightIDs = new ArrayList<String>();
        lightIDs.add(light.getId().toString());
        boolean result = smartHomeSecurityService.setLightIDs(lightIDs);
        assertEquals(result, true);

        result = smartHomeSecurityService.setTimeToKeepLightsOn("12:00 to 13:00");
        assertEquals(result, true);
    }
}
