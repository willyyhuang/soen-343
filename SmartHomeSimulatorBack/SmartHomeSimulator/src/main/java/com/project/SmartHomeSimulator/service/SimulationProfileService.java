package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.SimulationProfileRepository;
import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.SimulationConfig;
import com.project.SmartHomeSimulator.model.SimulationProfile;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationProfileService {

    @Autowired
    private SimulationProfileRepository simulationProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public SimulationProfileService(SimulationProfileRepository simulationProfileRepository, UserRepository userRepository){
        this.simulationProfileRepository = simulationProfileRepository;
        this.userRepository = userRepository;
    }

    // returns true if user was added or false if user exists
    public boolean addSimulationProfile(SimulationProfile simulationProfile, String username) {

        if(simulationProfileRepository.findByName(simulationProfile.getName()) == null){
            simulationProfileRepository.save(simulationProfile);
            return true;
        }
        return false;
    }

    // returns true if user was removed or false if user does not exist
    public boolean removeSimulationProfile(String name, String username) {
        SimulationProfile user = simulationProfileRepository.findByName(name);
        if(user != null) {
            simulationProfileRepository.deleteByName(name);
            return true;
        }
        return false;
    }

    // returns true if user was edited or false if user does not exist
    public boolean editHomeLocation(String name, String homeLocation, String username) {
        SimulationProfile simulationProfile = simulationProfileRepository.findByName(name);
        if (simulationProfile == null) {
            return false;
        }
        simulationProfile.setHomeLocation(homeLocation);
        simulationProfileRepository.save(simulationProfile);
        return true;
    }


    // returns true if profile was edited or false if user does not exist
    public boolean setCurrentSimulationProfile(String name, String username)
    {
        if(simulationProfileRepository.findByName(name) == null){
            return false;
        }
        User user = userRepository.findByUsername(username);
        user.setCurrentSimulationProfile(name);
        userRepository.save(user);
        return true;
    }

    public SimulationConfig getSimulationConfig(String username){
        User user = userRepository.findByUsername(username);
        SimulationConfig simulationConfig = new SimulationConfig(simulationProfileRepository.findAll(), simulationProfileRepository.findByName(user.getCurrentSimulationProfile()));
        return simulationConfig ;
    }
}
