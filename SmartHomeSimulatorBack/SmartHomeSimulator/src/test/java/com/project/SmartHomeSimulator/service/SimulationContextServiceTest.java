package com.project.SmartHomeSimulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.User;

import net.minidev.json.JSONObject;

/**
 * SimualtionContextService Unit tests
 */
@SpringBootTest
public class SimulationContextServiceTest {

    @Autowired
    private SimulationContextService simulationContextService;

    @Autowired
    private UserService userService;


    /**
     * load a house layout test
     */
    @Test
    public void loadLayout() {
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = new HomeLayout();
        homeLayout = homeLayout.readHomeLayout(homeLayoutFile);
        HomeLayout.windowCount--;
        HomeLayout result = simulationContextService.loadLayout(homeLayoutFile);
        homeLayout.getRoomList().get(0).getObjects().get(0).setId(result.getRoomList().get(0).getObjects().get(0).getId());
        assertEquals(result.toString(), homeLayout.toString());
    }

    /**
     * Star simulation in a wrong time
     */
    @Test
    public void startSimulation() {
        simulationContextService.startSimulation();
        boolean result = simulationContextService.getSimulationContext().isSimulationRunning();
        assertEquals(false, result);
    }

    @Test
    public void stopSimulation() {
        simulationContextService.stopSimulation();
        boolean result = simulationContextService.getSimulationContext().isSimulationRunning();
        assertEquals(false, result);
    }

    @Test
    public void setOutdoorTemp() {
        boolean result = simulationContextService.setOutsideTemp(12);
        assertEquals(true, result);
    }

    /**
     * Set inside temperature test
     */
    @Test
    public void setTime() {
        boolean result = simulationContextService.setTime("12:15");
        assertEquals(true, result);
    }

    /**
     * set date test
     */
    @Test
    public void setDate() {
        boolean result = simulationContextService.setDate("2020-08-25");
        assertEquals(true, result);
    }

    /**
     * set the current user in charge of the simulation test - switching users
     *
     * @throws JsonProcessingException
     */
    @Test
    public void setCurrentSimulationUser() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "string");
        ObjectMapper objectMapper = new ObjectMapper();
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\"}]}]\"}";
        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        boolean result = simulationContextService.setCurrentSimulationUser("testUser");
        assertEquals(true, result);
    }

    /**
     * Set auto mode test
     */
    @Test
    public void setAutoMode() {
        boolean result = simulationContextService.setAutoMode(true);
        assertEquals(true, result);
    }
}
