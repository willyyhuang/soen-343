package com.project.SmartHomeSimulator.module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Module {
    private String name;
    private String consoleMessage ="";

    public void logSuccess(String roomObjectName, String roomName, String action, String actor){
        String message = createSuccessfulLogMessage(roomObjectName,roomName,action,actor);
        this.consoleMessage += message + " ";
        logToFile(this.name, message);
    }

    public void logFail(String roomObjectName, String roomName, String action, String actor){
        String message = createUnsuccessfulLogMessage(roomObjectName,roomName,action,actor);
        this.consoleMessage += message + " ";
        logToFile(this.name, message);
    }

    public void logMessage(String message){
        this.consoleMessage += message + " ";
        logToFile(this.name, message);
    }

    private boolean logToFile(String fileName, String message){
        File file = new File(fileName+".txt");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(message);
            printWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createSuccessfulLogMessage(String roomObjectName, String roomName, String action, String actor){
        return "["+roomObjectName+"] " + roomName +" "+ roomObjectName +" was "+ action +" by "+ actor +"'s request";
    }
    
    private String createUnsuccessfulLogMessage(String roomObjectName, String roomName, String action, String actor){
        return "[Failed] " + action +" "+ roomObjectName +" in "+ roomName +", requested by "+ actor +", failed";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsoleMessage() {
        String temp = this.consoleMessage;
        this.consoleMessage = "";
        return temp;
    }

    public void setConsoleMessage(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }
}
