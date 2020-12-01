package com.project.SmartHomeSimulator.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.User;


/**
 * SimulationContext class is used to hold information pertaining the simulation context
 */
public class SimulationContext {

	private boolean simulationRunning;
	private int outsideTemp = 20;
	private String time;
	private String date;
	private User currentSimulationUser;
	private User awayModeUser;
	private List<User> simulationUsers;
	private boolean autoMode = false;
	private boolean awayMode = false;
	private int timeBeforeAuthoroties = 0;
	private String startLightsOn;
	private String endLightsOn;
	private HomeLayout homeLayout;
	private List<Monitor> monitors;
	private String summerMonths;
	private String winterMonths;
	private int summerTemp;
	private int winterTemp;
	private final File userProfilesJSON = new File("./src/main/resources/user_profiles.json.txt");
	private static int counter = 0;
	private int tempThreshold;

	private SimulationContext() {
		monitors = new ArrayList<Monitor>();
		this.monitors.add(SmartHomeSecurity.getInstance());
		this.monitors.add(SmartHomeHeating.getInstance());
	}

	public static SimulationContext getInstance(){
		if (simulationContext == null){
			simulationContext = new SimulationContext();
		}
		return simulationContext;
	}

	public int getSummerTemp() {
		return summerTemp;
	}

	public void setSummerTemp(int summerTemp) {
		this.summerTemp = summerTemp;
	}

	public int getWinterTemp() {
		return winterTemp;
	}

	public void setWinterTemp(int winterTemp) {
		this.winterTemp = winterTemp;
	}

	public void setWinterMonths(String winterMonths) {
		this.winterMonths = winterMonths;
	}

	public void setSummerMonths(String summerMonths) {
		this.summerMonths = summerMonths;
	}

	public String getSummerMonths() {
		return summerMonths;
	}

	public String getWinterMonths() {
		return winterMonths;
	}

	public static SimulationContext simulationContext = null;

	public String getEndLightsOn() {
		return endLightsOn;
	}

	public void setEndLightsOn(String endLightsOn) {
		this.endLightsOn = endLightsOn;
	}

	public String getStartLightsOn() {
		return startLightsOn;
	}

	public void setStartLightsOn(String startLightsOn) {
		this.startLightsOn = startLightsOn;
	}

	public int getTimeBeforeAuthoroties() {
		return timeBeforeAuthoroties;
	}

	public int getTempThreshold() {
		return tempThreshold;
	}

	public void setTempThreshold(int tempThreshold) {
		this.tempThreshold = tempThreshold;
	}

	public void setTimeBeforeAuthoroties(int timeBeforeAuthoroties) {
		this.timeBeforeAuthoroties = timeBeforeAuthoroties;
	}

	public List<User> getAllUsersInLocation(String room){
		List<User> usersInRoom = new ArrayList<>();
		for (User user : this.simulationUsers){
			if(user.getHomeLocation().equalsIgnoreCase(room)){
				usersInRoom.add(user);
			}
		}
		return usersInRoom;
	}

	public boolean isAutoMode() {
		return autoMode;
	}

	public void setAutoMode(boolean autoMode) {
		this.autoMode = autoMode;
	}

	public User getAwayModeUser() {
		return awayModeUser;
	}

	public void setAwayModeUser(User awayModeUser) {
		this.awayModeUser = awayModeUser;
	}

	public int getOutsideTemp() {
		return outsideTemp;
	}

	public void setOutsideTemp(int outsideTemp) {
		this.outsideTemp = outsideTemp;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getCurrentSimulationUser() {
		return currentSimulationUser;
	}

	public void setCurrentSimulationUser(User currentSimulationUser) {
		this.currentSimulationUser = currentSimulationUser;
	}

	public List<User> getSimulationUsers() {
		counter++;
		if (counter == 1) {
			loadUserProfiles();
		}
		return simulationUsers;
	}
	
	public void loadUserProfiles() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			simulationUsers = mapper.readValue(userProfilesJSON, new TypeReference<List<User>>(){});
			for (User user : simulationUsers){
				if(!user.getHomeLocation().equalsIgnoreCase("outside")) {
					HomeLayout.usersInHome++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSimulationUsers(List<User> simulationUsers) {
		this.simulationUsers = simulationUsers;
	}

	public HomeLayout getHomeLayout() {
		return homeLayout;
	}

	public void setHomeLayout(HomeLayout homeLayout) {
		this.homeLayout = homeLayout;
	}

	public boolean isSimulationRunning() {
		return simulationRunning;
	}

	public void setSimulationRunning(boolean simulationRunning) {
		this.simulationRunning = simulationRunning;
	}

	public void addMonitor(Monitor monitor){
		this.monitors.add(monitor);
	}

	public void removeMonitor(Monitor monitor){
		this.monitors.remove(monitor);
	}

	public void notifyMonitors(User user){
		if (awayModeUser != null) {
			for (Monitor monitor : this.monitors) {
				monitor.update(awayModeUser.getName(), user);
			}
		}
	}

	public boolean isAwayMode() {
		return awayMode;
	}

	public void setAwayMode(boolean awayMode) {
		this.awayMode = awayMode;
	}

	@Override
	public String toString() {
		return "SimulationContext [ outsideTemp=" + outsideTemp + ", time=" + time
				+ ", date=" + date + ", currentSimulationUser=" + currentSimulationUser + ", simulationUsers="
				+ simulationUsers + ", homeLayout=" + homeLayout + ", simulationRunning=" + simulationRunning + "]";
	}
}
