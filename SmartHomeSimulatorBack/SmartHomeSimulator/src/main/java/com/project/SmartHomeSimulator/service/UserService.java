package com.project.SmartHomeSimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.HouseLayout;
import com.project.SmartHomeSimulator.model.User;

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

    public User identifyUser (User user)
    {
        User found = userRepository.findByUsername(user.getUsername());
        if(found == null){
            return null;
        }
        if(user.getPassword().equals(found.getPassword())){
            return user;
        }
        return null;
    }
    public long removeUser (String username)
    {
        return userRepository.deleteByUsername(username);
    }

    public int editUsername ( User user, String newUsername)
    {
        User oldUser =  userRepository.findByUsername(user.getUsername());
        if (oldUser == null)
            return 0;
        User newUser = new User();
        newUser.setUsername(newUsername);
        newUser.setPassword(oldUser.getPassword());
        newUser.setHomeLocation(oldUser.getHomeLocation());
        removeUser(oldUser.getUsername());
        registrationService.createUser(newUser);
        return 1;
    }

    public int editPassword (User user)
    {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        user.setHomeLocation(currentUser.getHomeLocation());
        removeUser(currentUser.getUsername());
        registrationService.createUser(user);
        return 1;
    }

    public int editHomeLocation (User user)
    {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        user.setPassword(currentUser.getPassword());
        removeUser(currentUser.getUsername());
        registrationService.createUser(user);
        return 1;
    }
    
    public HouseLayout loadLayout(User user) 
    {
    	User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return null;
        currentUser.setFileLayout(user.getFileLayout());
        HouseLayout layoutUploader = new HouseLayout(currentUser.getFileLayout());
        return layoutUploader;
    }

}
