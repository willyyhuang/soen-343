package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * UserService Unit Tests
 */
public class UserServiceTest {

    private UserService userService;
    private SimulationContext simulationContext;
    private User user;

    @Before
    public void setup()
    {
        simulationContext = new SimulationContext();
        userService = new UserService(simulationContext);

        user = new User();
        user.setRole(Role.PARENT);
        user.setName("name");
        user.setHomeLocation("homeLocation");

        userService.addUser(user);
    }

    @Test
    public void addUser(){
        User testUser = new User();
        testUser.setName("testUserName");
        testUser.setRole(Role.CHILD);
        testUser.setHomeLocation("testUserHomeLocation");

        //successfully adding a user, must return true
        Assert.assertTrue (userService.addUser(testUser));

        //unsuccessful to add a user, must return false
        Assert.assertFalse (userService.addUser(testUser));
    }

    @Test
    public void removeUser(){
        //successfully removing a user, must return true
        Assert.assertTrue (userService.removeUser(user.getName()));

        //unsuccessful to remove a user, must return false
        Assert.assertFalse (userService.removeUser(user.getName()));
    }

    @Test
    public void editUser(){
        //successfully edit a user's name, must return true
        Assert.assertTrue (userService.editUser(user.getName(), "testName"));

        //unsuccessful to edit a user's name, must return false
        Assert.assertFalse (userService.editUser("randomName", "testName"));
    }

    @Test
    public void editHomeLocation(){
        //successfully edit a user's home location, must return true
        Assert.assertTrue (userService.editHomeLocation(user.getName(), "testHomeLocation"));

        //unsuccessful to edit a user's home location, must return false
        Assert.assertFalse (userService.editHomeLocation("randomName", "testName"));
    }
}
