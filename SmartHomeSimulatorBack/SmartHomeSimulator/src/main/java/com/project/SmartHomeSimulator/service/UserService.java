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
    private RegistrationService registrationService;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public String identifyUser (User user)
    {
        User found = userRepository.findByUsername(user.getUsername());
        if(found == null){
            return null;
        }
        if(user.getPassword().equals(found.getPassword())){
            return user.getUsername();
        }
        return null;
    }
    public long removeUser (String username)
    {
        return userRepository.deleteByUsername(username);
    }

    public int editPassword (User user)
    {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setPassword(user.getPassword());
        userRepository.save(currentUser);
        return 1;
    }

    public int editHomeLocation (User user)
    {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setHomeLocation(user.getHomeLocation());
        userRepository.save(currentUser);
        return 1;
    }

}
