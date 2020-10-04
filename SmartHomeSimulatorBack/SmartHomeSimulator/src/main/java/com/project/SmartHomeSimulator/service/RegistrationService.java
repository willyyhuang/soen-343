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

    public void createUser(final User user)
    {
        userRepository.save(user);
    }
}
