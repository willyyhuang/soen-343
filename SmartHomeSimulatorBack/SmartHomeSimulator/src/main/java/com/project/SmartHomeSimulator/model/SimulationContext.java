package com.project.SmartHomeSimulator.model;

import org.springframework.stereotype.Component;

import java.util.List;


/**
 * SimulationContext class is used to hold information pertaining the simulation context
 */
@Component
public class SimulationContext {
	private int insideTemp;
	private int outsideTemp;
	private String time;
	private String date;
	private User currentSimulationUser;
	private List<User> simulationUsers;
	private HomeLayout homeLayout;
	private boolean simulationRunning;

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

	@Override
	public String toString() {
		return "SimulationContext [insideTemp=" + insideTemp + ", outsideTemp=" + outsideTemp + ", time=" + time
				+ ", date=" + date + ", currentSimulationUser=" + currentSimulationUser + ", simulationUsers="
				+ simulationUsers + ", homeLayout=" + homeLayout + ", simulationRunning=" + simulationRunning + "]";
	}

	
}
