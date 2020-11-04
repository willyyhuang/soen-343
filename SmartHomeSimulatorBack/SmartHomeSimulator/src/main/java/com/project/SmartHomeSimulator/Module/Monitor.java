package com.project.SmartHomeSimulator.Module;

import com.project.SmartHomeSimulator.model.User;

public interface Monitor {
    public void update(String awayModeUser, User user);
}
