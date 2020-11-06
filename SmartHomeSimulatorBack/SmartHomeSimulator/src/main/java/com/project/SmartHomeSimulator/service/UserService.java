package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.module.SmartHomeCoreFunctionality;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService {

    private SimulationContext simulationContext = SimulationContext.getInstance();
    private SmartHomeCoreFunctionality smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
    private SmartHomeSecurity smartHomeSecurity = SmartHomeSecurity.getInstance();

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
            List<RoomObject> lightsInRoom = new ArrayList<>();
            if(simulationContext.getHomeLayout() != null) {
                lightsInRoom = simulationContext.getHomeLayout().allLights(user.getHomeLocation());
            }
            if(simulationContext.isAutoMode()){
                for (RoomObject light : lightsInRoom) {
                    smartHomeCoreFunctionality.objectStateSwitcher(user.getHomeLocation(), light.getId().toString(), true);
                }
            }
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
            if (simulationContext.getHomeLayout() != null) {
                simulationContext.getHomeLayout().removeUsersInHome(toBeRemovedUser.getHomeLocation());
            }
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
    public ResponseAPI editHomeLocation(String name, String homeLocation) {
        User user = findUserByName(name);
        simulationContext.notifyMonitors(user);
        ResponseAPI response = new ResponseAPI();
        if(!smartHomeSecurity.isAlertModeOn()) {
            List<User> usersInOldLocation = new ArrayList<>();
            if (user != null) {
                usersInOldLocation = simulationContext.getAllUsersInLocation(user.getHomeLocation());
            }
            List<RoomObject> lightsInRoom = new ArrayList<>();
            if (simulationContext.getHomeLayout() != null) {
                lightsInRoom = simulationContext.getHomeLayout().allLights(user.getHomeLocation());
            }
            if (usersInOldLocation.size() == 1 && simulationContext.isAutoMode()) {
                for (RoomObject light : lightsInRoom) {
                    smartHomeCoreFunctionality.objectStateSwitcher(user.getHomeLocation(), light.getId().toString(), false);
                }
            }
            if (user != null) {
                user.setHomeLocation(homeLocation);
                if (simulationContext.getHomeLayout() != null) {
                    lightsInRoom = simulationContext.getHomeLayout().allLights(user.getHomeLocation());
                }
                if (simulationContext.isAutoMode()) {
                    for (RoomObject light : lightsInRoom) {
                        smartHomeCoreFunctionality.objectStateSwitcher(user.getHomeLocation(), light.getId().toString(), true);
                    }
                }
                if (simulationContext.getHomeLayout() != null) {
                    simulationContext.getHomeLayout().addUsersInHome(user.getHomeLocation());
                    simulationContext.getHomeLayout().removeUsersInHome(user.getHomeLocation());
                }
                response.success = true;
                response.awayMode = false;
                response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
                return response;
            }
        }else {
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            return response;
        }
        response.success = false;
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        return response;
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