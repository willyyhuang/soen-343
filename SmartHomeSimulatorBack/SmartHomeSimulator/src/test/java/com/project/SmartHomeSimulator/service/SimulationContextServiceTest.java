package com.project.SmartHomeSimulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.module.SimulationContext;
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
     * Set Outside temperature test
     */
    @Test
    public void startSimulation() {
    	simulationContextService.startSimulation();
    	boolean result = simulationContextService.getSimulationContext().isSimulationRunning();
    	assertEquals(result, true);
    }
    
    @Test
    public void stopSimulation() {
    	simulationContextService.stopSimulation();
    	boolean result = simulationContextService.getSimulationContext().isSimulationRunning();
    	assertEquals(result, false);
    }
    
    @Test
    public void getSimulationContext() {
    	SimulationContext otherSimulationContextService = new SimulationContext();
    	otherSimulationContextService.clone(simulationContextService.getSimulationContext());
    	SimulationContext result = simulationContextService.getSimulationContext();
    	assertEquals(result.toString(), otherSimulationContextService.toString());
    }
    
    @Test
    public void setInsideTemp() {
    	boolean result = simulationContextService.setInsideTemp(20);
    	assertEquals(result, true);
    }
    
    @Test
    public void setOutsideTemp(){
        boolean result = simulationContextService.setOutsideTemp(12);
        assertEquals(result,true);
    }

    /**
     * Set inside temperature test
     */
    @Test
    public void setTime(){
        boolean result = simulationContextService.setTime("12:15");
        assertEquals(result,true);
    }

    /**
     * set date test
     */
    @Test
    public void setDate(){
        boolean result = simulationContextService.setDate("2020-08-25");
        assertEquals(result,true);
    }

    /**
     * set the current user in charge of the simulation test - switching users
     * @throws JsonProcessingException
     */
    @Test
    public void  setCurrentSimulationUser() throws JsonProcessingException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", "testUser");
        jsonUser.put("role", "PARENT");
        jsonUser.put("homeLocation", "bedroom");
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonUser.toString(), User.class);
        userService.addUser(user);
        boolean result = simulationContextService.setCurrentSimulationUser("testUser");
        assertEquals(result,true);
    }

    /**
     * choosing to block a window test
     */
//    @Test
//    public void blockWindow(){
//        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\", \"status\": \"true\"}]}]\"}";
//        HomeLayout homeLayout = simulationContextService.loadLayout(homeLayoutFile);
//        Room room = homeLayout.getRoomList().get(0);
//        RoomObject window = room.getObjects().get(0);
//        boolean result = simulationContextService.blockWindow(room.getName(),window.getId().toString(),false);
//        assertEquals(result,true);
//    }

    /**
     * load a house layout test
     */
    @Test
    public void loadLayout(){
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"string\", \"objects\":[{\"objectType\": \"WINDOW\", \"status\": \"true\"}]}]\"}";
        HomeLayout homeLayout = new HomeLayout();
        homeLayout = homeLayout.readHomeLayout(homeLayoutFile);
        HomeLayout result = simulationContextService.loadLayout(homeLayoutFile);
        homeLayout.getRoomList().get(0).getObjects().get(0).setId(result.getRoomList().get(0).getObjects().get(0).getId());
        assertEquals(result.toString(),homeLayout.toString());
    }


}
