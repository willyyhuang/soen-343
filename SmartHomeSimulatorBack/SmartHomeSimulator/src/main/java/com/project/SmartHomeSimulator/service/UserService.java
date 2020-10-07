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

    // Returns username if right password, Not Found if user does not exist and No Match if wrong password
    public String identifyUser (User user)
    {
        User found = userRepository.findByUsername(user.getUsername());
        if(found == null){
            return "Not Found";
        }
        if(user.getPassword().equals(found.getPassword())){
            return user.getUsername();
        }
        return "No Match";
    }

    //returns 0 if user was not found and 1 if successfully deleted
    public long removeUser (String username)
    {
        return userRepository.deleteByUsername(username);
    }

    //returns 0 if user was not found and 1 if successfully edited
    public int editPassword (User user)
    {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser == null)
            return 0;
        currentUser.setPassword(user.getPassword());
        userRepository.save(currentUser);
        return 1;
    }

    //returns 0 if user was not found and 1 if successfully edited
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
