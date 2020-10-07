package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("registrationService")
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    //returns 0 if user exists returns 1 if successfully created
    public int createUser(final User user)
    {
        if(userRepository.findByUsername(user.getUsername()) != null){
            return 0;
        }
        userRepository.save(user);
        return 1;
    }
}
