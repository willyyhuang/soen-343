package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public String identifyUser (User user)
    {
        User found = userRepository.findByUsername(user.getUsername());
        if(user.getPassword().equals(found.getPassword())){
            return user.getUsername();
        }
        return null;
    }

}
