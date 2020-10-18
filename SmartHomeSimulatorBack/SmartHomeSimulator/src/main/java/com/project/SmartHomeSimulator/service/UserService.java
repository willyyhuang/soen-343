package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private SimulationContext simulationContext;

    @Autowired
    public UserService(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    //adds a new user to the simulation context users list
    public boolean addUser(User user) {
        User userAlreadyExist = findUserByName(user.getName());
        if (userAlreadyExist == null) {
            if (simulationContext.getSimulationUsers() == null) {
                simulationContext.setSimulationUsers(new ArrayList<User>());
            }
            simulationContext.getSimulationUsers().add(user);
            return true;
        }
        return false;
    }

    //returns false if user was not found and true if successfully deleted
    public boolean removeUser(String name) {
        User toBeRemovedUser = findUserByName(name);
        if (toBeRemovedUser != null) {
            simulationContext.getSimulationUsers().remove(toBeRemovedUser);
            return true;
        }
        return false;
    }

    public boolean editUser(String name, String newName) {
        User user = findUserByName(name);
        if (user != null) {
            user.setName(newName);
            return true;
        }
        return false;
    }

    //adds new home location to user
    public boolean editHomeLocation(String name, String homeLocation) {
        User currentUser = simulationContext.getCurrentSimulationUser();
        User user = findUserByName(name);

        if (currentUser != null && currentUser.getName().equals("name")) {
            currentUser.setHomeLocation(homeLocation);
            return true;
        } else if (user != null) {
            user.setHomeLocation(homeLocation);
            return true;
        }
        return false;
    }

    //finds user by name and returns it
    User findUserByName(String name) {
        List<User> users = simulationContext.getSimulationUsers();
        if (users != null) {
            for (User user : users) {
                if (user.getName().equals(name)) {
                    return user;
                }
            }
        }
        return null;
    }
}