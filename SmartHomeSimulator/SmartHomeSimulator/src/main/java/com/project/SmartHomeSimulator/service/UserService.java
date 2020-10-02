package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.UserDao;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("fakeDao") UserDao userDao){
        this.userDao = userDao;
    }

    public int addUser (User user){
        return userDao.addUser(user);
    }
}
