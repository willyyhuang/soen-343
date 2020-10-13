package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.UserRepository;
import com.project.SmartHomeSimulator.model.APIResponseLogin;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Returns user if the password match or null if user does not exist or password does not match
    public APIResponseLogin identifyUser(User user) {
        User found = userRepository.findByUsername(user.getUsername());
        APIResponseLogin response = new APIResponseLogin();
        response.setUser(found);
        if (found == null) {
            response.setSuccess(false);
            return null;
        } else if (user.getPassword().equals(found.getPassword())) {
            response.setSuccess(true);
            return response;
        }
        response.setSuccess(false);
        return null;
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

    // Returns user if it exists
    public APIResponseLogin getUser(String username) {
        User found = userRepository.findByUsername(username);
        APIResponseLogin response = new APIResponseLogin();
        response.setUser(found);
        if (found == null) {
            response.setSuccess(false);
            return null;
        }

        response.setSuccess(true);
        return response;

    }
}
