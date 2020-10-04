package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

}
