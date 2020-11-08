package com.project.SmartHomeSimulator.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.User;


/**
 * SimulationContext class is used to hold information pertaining the simulation context
 */
public class SimulationContext {

	private int insideTemp;
	private int outsideTemp;
	private String time;
	private String date;
	private User currentSimulationUser;
	private User awayModeUser;
	private List<User> simulationUsers;
	private HomeLayout homeLayout;
	private boolean simulationRunning;
	private List<Monitor> monitors;
	private final File userProfilesJSON = new File("src\\main\\resources\\user_profiles.json.txt");
	private static int counter = 0;
	public static SimulationContext simulationContext = null;

	public SimulationContext() {
		monitors = new ArrayList<Monitor>();
		this.monitors.add(SmartHomeSecurity.getInstance());
	}

	public static SimulationContext getInstance(){
		if (simulationContext == null){
			simulationContext = new SimulationContext();
		}
		return simulationContext;
	}
	public void clone(SimulationContext simulationContext) {
		this.insideTemp = simulationContext.insideTemp;
		this.outsideTemp = simulationContext.outsideTemp;
		this.time = simulationContext.time;
		this.date = simulationContext.date;
		this.currentSimulationUser = simulationContext.currentSimulationUser;
		this.simulationUsers = simulationContext.simulationUsers;
		this.homeLayout = simulationContext.homeLayout;
		this.simulationRunning = simulationContext.simulationRunning;
	}

	public User getAwayModeUser() {
		return awayModeUser;
	}

	public void setAwayModeUser(User awayModeUser) {
		this.awayModeUser = awayModeUser;
	}

	public int getInsideTemp() {
		return insideTemp;
	}

	public void setInsideTemp(int insideTemp) {
		this.insideTemp = insideTemp;
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

	@Override
	public String toString() {
		return "SimulationContext [insideTemp=" + insideTemp + ", outsideTemp=" + outsideTemp + ", time=" + time
				+ ", date=" + date + ", currentSimulationUser=" + currentSimulationUser + ", simulationUsers="
				+ simulationUsers + ", homeLayout=" + homeLayout + ", simulationRunning=" + simulationRunning + "]";
	}
}
