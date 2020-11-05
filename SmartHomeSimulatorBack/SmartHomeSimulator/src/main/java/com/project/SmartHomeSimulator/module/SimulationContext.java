package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * SimulationContext class is used to hold information pertaining the simulation context
 */
public class SimulationContext {

	private boolean simulationRunning;
	private int insideTemp;
	private int outsideTemp;
	private String time;
	private String date;
	private User currentSimulationUser;
	private User awayModeUser;
	private List<User> simulationUsers;
	private boolean autoMode = false;
	private HomeLayout homeLayout;
	private List<Monitor> monitors;

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
		return simulationUsers;
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
