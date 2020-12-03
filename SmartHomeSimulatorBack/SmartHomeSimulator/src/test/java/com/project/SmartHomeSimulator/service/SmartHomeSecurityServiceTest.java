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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SmartHomeSecurityServiceTest {

    @Autowired
    SimulationContextService simulationContextService = new SimulationContextService();

    @Autowired
    SmartHomeCoreFunctionalityService smartHomeCoreFunctionalityService = new SmartHomeCoreFunctionalityService();

    @Autowired
    SmartHomeSecurityService smartHomeSecurityService = new SmartHomeSecurityService();

    @Autowired
    private UserService userService = new UserService();

    private void setup() {
        userService = new UserService();
        User user = new User();
        user.setRole(Role.PARENT);
        user.setName("testUser");
        user.setHomeLocation("outside");
        userService.addUser(user);
        simulationContextService.setCurrentSimulationUser("name");

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"bedroom\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"building entrance\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"garage\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}]\"}";
        simulationContextService.loadLayout(homeLayoutFile);
        simulationContextService.startSimulation();
    }

    /**
     * set away mode
     * Use case ID = 7
     */
    @Test
    public void awayMode_7() throws JsonProcessingException {
        setup();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"backyard\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}, {\"name\":\"building entrance\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}, {\"name\":\"garage\", \"objects\":[{\"objectType\": \"DOOR\"}, {\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject door = room.getObjects().get(0);
        RoomObject window = room.getObjects().get(1);

        //on
        smartHomeCoreFunctionalityService.openCloseDoors(room.getName(), door.getId().toString(), true);
        smartHomeCoreFunctionalityService.openCloseWindow(room.getName(), window.getId().toString(), true);

        List<User> users= SimulationContext.getInstance().getSimulationUsers();
        for(User userInHome : users){
            userService.editHomeLocation(userInHome.getName(), "outside");
        }

        boolean result = smartHomeSecurityService.setAwayMode(true);
        assertEquals(true, result);

        assertEquals(false, window.isStatus());
        assertEquals(false, door.isStatus());
    }

    /**
     * Time before calling authorities set test
     * Use case ID = 4
     */
    @Test
    public void setTimeBeforeAuthorities_4() throws JsonProcessingException {
        setup();

        boolean result = smartHomeSecurityService.setTimeBeforeAuthorities(12);
        assertEquals(true, result);
    }

    /**
     * light and time interval set for away mode
     * Use case ID = 4
     */
    @Test
    public void setTimeInterval_4() throws JsonProcessingException {
        setup();

        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"LIGHT\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        Room room = homeLayout.getRoomList().get(0);
        RoomObject light = room.getObjects().get(0);

        List<String> lightIDs = new ArrayList<String>();
        lightIDs.add(light.getId().toString());
        boolean result = smartHomeSecurityService.setLightIDs(lightIDs);
        assertEquals(true, result);

        result = smartHomeSecurityService.setTimeToKeepLightsOn("12:00 to 13:00");
        assertEquals(true, result);
    }
}
