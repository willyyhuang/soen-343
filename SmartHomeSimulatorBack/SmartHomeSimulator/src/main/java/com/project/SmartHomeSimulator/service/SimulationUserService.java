package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.SimulationUserRepository;
import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.SimulationConfig;
import com.project.SmartHomeSimulator.model.SimulationUser;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationUserService {

    @Autowired
    private SimulationUserRepository simulationUserRepository;
    @Autowired
    private UserRepository userRepository;
    private SimulationConfig simulationConfig;
    @Autowired
    public SimulationUserService(SimulationUserRepository simulationUserRepository, UserRepository userRepository){
        this.simulationUserRepository = simulationUserRepository;
        this.userRepository = userRepository;
        simulationConfig = new SimulationConfig(simulationUserRepository.findAll());
    }

    // returns true if user was added or false if user exists
    public SimulationConfig addSimulationUser(SimulationUser simulationUser) {

        if(simulationUserRepository.findByUsername(simulationUser.getUsername()) == null){
            simulationUserRepository.save(simulationUser);
            simulationConfig = new SimulationConfig(simulationUserRepository.findAll());
            simulationConfig.setSuccess(true);
        }
        simulationConfig.setSuccess(false);

        return simulationConfig;
    }

    // returns true if user was removed or false if user does not exist
    public SimulationConfig removeUser(String username) {
        SimulationUser user = simulationUserRepository.findByUsername(username);
        if(user != null) {
            simulationUserRepository.deleteByUsername(username);
            simulationConfig = new SimulationConfig(simulationUserRepository.findAll());
            simulationConfig.setSuccess(true);
        }
        simulationConfig.setSuccess(false);
        return simulationConfig;
    }

    // returns true if user was edited or false if user does not exist
    public SimulationConfig editHomeLocation(String username, String homeLocation) {
        SimulationUser simulationUser = simulationUserRepository.findByUsername(username);
        if (simulationUser == null) {
            simulationConfig.setSuccess(false);
            return simulationConfig;
        }
        simulationUser.setHomeLocation(homeLocation);
        simulationUserRepository.save(simulationUser);
        simulationConfig = new SimulationConfig(simulationUserRepository.findAll());
        simulationConfig.setSuccess(true);
        return simulationConfig;
    }


    // returns true if user was edited or false if user does not exist
    public SimulationConfig setCurrentSimulationUser(User user, String username)
    {
        if(simulationUserRepository.findByUsername(username) == null){
            simulationConfig.setSuccess(false);
            return simulationConfig;
        }
        user.setCurrentSimulationUser(username);
        userRepository.save(user);
        simulationConfig.setCurrentSimulationUser(simulationUserRepository.findByUsername(username));
        simulationConfig.setSuccess(true);
        return simulationConfig;
    }
}
