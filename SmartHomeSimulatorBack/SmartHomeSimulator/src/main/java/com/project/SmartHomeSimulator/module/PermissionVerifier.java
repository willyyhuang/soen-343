package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.User;

public class PermissionVerifier {

    public static PermissionVerifier permissionVerifier= null;
    public SmartHomeCoreFunctionality smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();

    public static PermissionVerifier getInstance(){
        if(permissionVerifier == null){
            permissionVerifier = new PermissionVerifier();
        }
        return permissionVerifier;
    }

    public boolean verifyPermission(User user, String action, String roomName, String id, boolean state){
        boolean success = true;
        Role role = user.getRole();
        switch(role){
            case PARENT:
                success = smartHomeCoreFunctionality.objectStateSwitcher(roomName,id,state);
                if (state == true)
                    System.out.println(user.getName() + ", a parent, opened the " + action);
                else
                    System.out.println(user.getName() + ", a parent, closed the " + action);
                break;
            case GUEST:
            case CHILD:
                if(!user.getHomeLocation().equalsIgnoreCase(roomName) || action.equals("door") ){
                    success = false;
                }
                else
                    success = smartHomeCoreFunctionality.objectStateSwitcher(roomName,id,state);
                break;
            default:
                success = false;
                break;
        }
        return success;
    }
}
