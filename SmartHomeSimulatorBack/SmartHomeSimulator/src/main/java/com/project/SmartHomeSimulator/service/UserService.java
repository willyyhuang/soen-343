package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.SimulationProfileRepository;
import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.APIResponseLogin;
import com.project.SmartHomeSimulator.model.SimulationConfig;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimulationProfileRepository simulationProfileRepository;

    @Autowired
    public UserService(UserRepository userRepository, SimulationProfileRepository simulationProfileRepository) {
        this.userRepository = userRepository;
        this.simulationProfileRepository = simulationProfileRepository;
    }

    // Returns user if the password match or null if user does not exist or password does not match
    public APIResponseLogin identifyUser(User user) {
        User found = userRepository.findByUsername(user.getUsername());
        SimulationConfig simulationConfig = new SimulationConfig(simulationProfileRepository.findAll(), simulationProfileRepository.findByName(found.getCurrentSimulationProfile()));
        APIResponseLogin response = new APIResponseLogin(simulationConfig);
        if (user.getPassword().equals(found.getPassword())) {
            response.setSuccess(true);
            return response;
        }
        response.setSuccess(false);
        return response;
    }

    //returns 0 if user was not found and 1 if successfully deleted
    public long removeUser(String username) {
        return userRepository.deleteByUsername(username);
    }

    //returns 0 if user was not found and 1 if successfully edited
    public int editPassword(User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setPassword(user.getPassword());
        userRepository.save(currentUser);
        return 1;
    }


    //returns 0 if user was not found and 1 if successfully edited
    public int editInsideTemp(User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setInsideTemp (user.getInsideTemp() );
        userRepository.save(currentUser);
        return 1;
    }

    //returns 0 if user was not found and 1 if successfully edited
    public int editOutsideTemp(User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setOutsideTemp(user.getInsideTemp() );
        userRepository.save(currentUser);
        return 1;
    }

    //returns 0 if user was not found and 1 if successfully edited
    public int editTime(User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setTime (user.getTime() );
        userRepository.save(currentUser);
        return 1;
    }

    //returns 0 if user was not found and 1 if successfully edited
    public int editDate(User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setDate (user.getDate() );
        userRepository.save(currentUser);
        return 1;
    }
 
    // Returns user if it exists or null
    public User getUser(String username) {
        User found = userRepository.findByUsername(username);
        if (found == null) {
            return null;
        }
        return found;
    }
}
