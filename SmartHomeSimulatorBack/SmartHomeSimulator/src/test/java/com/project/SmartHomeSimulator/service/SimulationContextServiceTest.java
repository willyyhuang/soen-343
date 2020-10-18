package com.project.SmartHomeSimulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SimulationContextServiceTest {

    @Autowired
    private SimulationContextService simulationContextService;

    @Autowired
    private UserService userService;

    @Test
    public void setTime(){
        boolean result = simulationContextService.setTime("12:15");
        assertEquals(result,true);
    }

    @Test
    public void setDate(){
        boolean result = simulationContextService.setDate("2020-08-25");
        assertEquals(result,true);
    }

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

    @Test
    public void blockWindow(){

    }


}
