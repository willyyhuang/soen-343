package com.project.SmartHomeSimulator.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.module.SimulationContext;

@Service("userService")
public class UserService {

    private static SimulationContext simulationContext = SimulationContext.getInstance();
    private final File userProfilesFile = new File("src\\main\\resources\\user_profiles.json.txt");
    
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
            // adding to user profiles file
            
            try {
            	String jsonString = new Scanner(userProfilesFile).useDelimiter("\\Z").next();
            	StringBuilder builder = new StringBuilder(jsonString);
            	if (jsonString.length() <= 2) { // in case user profiles file is empty, we remove the "," separating elements
            		builder.insert(jsonString.length() - 1, user.toString());
            	} else {
            		builder.insert(jsonString.length() - 1, "," + user.toString());
            	}
            	
            	// writing the new user to the file
            	FileWriter myWriter = new FileWriter(userProfilesFile);
                myWriter.write(builder.toString());
                myWriter.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
				e.printStackTrace();
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
    public User findUserByName(String name) {
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