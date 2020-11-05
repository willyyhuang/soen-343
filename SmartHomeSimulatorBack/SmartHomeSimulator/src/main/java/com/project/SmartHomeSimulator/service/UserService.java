package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.module.SimulationContext;
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

    /**
     * adds a new user to the simulation context users list
     *
     * @param user
     * @return - true if successful false if otherwise
     */
    public boolean addUser(User user) {
        User userAlreadyExist = findUserByName(user.getName());
        if (userAlreadyExist == null) {
            if (simulationContext.getSimulationUsers() == null) {
                simulationContext.setSimulationUsers(new ArrayList<User>());
            }
            simulationContext.getSimulationUsers().add(user);
            if (simulationContext.getHomeLayout() != null){
                simulationContext.getHomeLayout().addUsersInHome(user.getHomeLocation());
                simulationContext.notifyMonitors(user);
            }
            return true;
        }
        return false;
    }


    /**
     * returns false if user was not found and true if successfully deleted
     *
     * @param name
     * @return - true if successful false if otherwise
     */
    public boolean removeUser(String name) {
        User toBeRemovedUser = findUserByName(name);
        if (toBeRemovedUser != null) {
            simulationContext.getSimulationUsers().remove(toBeRemovedUser);
            simulationContext.getHomeLayout().removeUsersInHome(toBeRemovedUser.getHomeLocation());
            return true;
        }
        return false;
    }

    /**
     * returns false if user was not found and true if successfully deleted
     *
     * @param name
     * @param newName
     * @return - true if successful false if otherwise
     */
    public boolean editUser(String name, String newName) {
        User user = findUserByName(name);
        if (user != null) {
            user.setName(newName);
            return true;
        }
        return false;
    }

    /**
     * adds new home location to user
     *
     * @param name
     * @param homeLocation
     * @return - true if successful false if otherwise
     */
    public boolean editHomeLocation(String name, String homeLocation) {
        User user = findUserByName(name);

        if (user != null) {
            user.setHomeLocation(homeLocation);
            simulationContext.getHomeLayout().addUsersInHome(user.getHomeLocation());
            simulationContext.notifyMonitors(user);
            return true;
        }
        return false;
    }

    /**
     * finds user by name and returns it
     *
     * @param name
     * @return - true if successful false if otherwise
     */
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