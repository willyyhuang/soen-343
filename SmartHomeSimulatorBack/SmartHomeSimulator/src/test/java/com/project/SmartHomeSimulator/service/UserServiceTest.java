package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.User;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

/**
 * UserService Unit Tests
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService = new UserService();

    @Autowired
    SimulationContextService simulationContextService = new SimulationContextService();

    private User user;


    public void setup()
    {
        Random r = new Random();
        int num = r.nextInt(10000000);

        user = new User();
        user.setRole(Role.PARENT);
        user.setName("testUserName" + num);
        user.setHomeLocation("bedroom");
        userService.addUser(user);
        String homeLayoutFile = "{\"roomList\":\"[{\"name\":\"bedroom\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"building entrance\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}, {\"name\":\"garage\", \"objects\":[{\"objectType\": \"AC\"}, {\"objectType\": \"HEATER\"}]}]\"}";
        simulationContextService.loadLayout(homeLayoutFile);
    }

    @Test
    public void addUser() {
        setup();
        Random r = new Random();
        int num = r.nextInt(10000000);
        User testUser = new User();
        testUser.setName("testUserName" + num);
        testUser.setRole(Role.CHILD);
        testUser.setHomeLocation("outside");

        boolean result = userService.addUser(testUser);
        //successfully adding a user, must return true
        Assert.assertTrue(result);

        result = userService.addUser(testUser);
        //unsuccessful to add a user, must return false
        Assert.assertFalse(result);
    }

    @Test
    public void removeUser() {
        setup();

        //successfully removing a user, must return true
        Assert.assertTrue(userService.removeUser(user.getName()));
    }

    @Test
    public void editUser() {
        setup();

        //successfully edit a user's name, must return true
        Assert.assertTrue(userService.editUser(user.getName(), "testName"));

        //unsuccessful to edit a user's name, must return false
        Assert.assertFalse(userService.editUser("randomName", "testName"));
    }

    @Test
    public void editHomeLocation() {
        setup();

        //successfully edit a user's home location, must return true
        Assert.assertTrue (userService.editHomeLocation(user.getName(), "kitchen").success);
    }
}
