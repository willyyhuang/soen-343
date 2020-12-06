# Smart Home Simulator - Introduction

### Description

This is a repo for the SOEN343 project of a Smart Home Simulation. Using an MVC structure the goal is to build:
* Smart Home Simulator (SHS)
* House Layout/ Home Dashboard
* Simulation Parameters
* Smart Home Modules
* Smart Home Core Functionality (SHC)
* Smart Home Security Module (SHP)
* Smart Heating Module (SHH)

### Team Members

* Wei Chen Huang
* Jennifer El-Asmar
* Karin Kazarian
* Hoda Nourbakhsh
	
### Technology used & Programming langages

* jdk 11 - Java
* Spring Boot
* Maven
* React
* Java Script - CSS

### What design patterns were used
Singleton design pattern:

We use the singleton design pattern in the SmartHomeHeater (SHH) class to restrict the instantiation of this class to only once. The reason behind implementing this pattern is because we need only one instance of the SHH module to keep it consistent throughout a simulation.  

Observer design pattern:

As for the functionality of the SHH, we implement it using the observer pattern. The SHH (observer), is subscribed to the smart home simulator and security (observables) to monitor the presence of individuals in rooms and the away mode state, respectively. This way, the SHH module can act accordingly when changes happen during a simulation.  

# Installation

### Installation for Front-End
Smart Home Simulator uses React.js for user interface

Install yarn or npm for front-end package manager

#### with Yarn
```
cd SmartHomeSimulatorFront
yarn install
```

Start local server by typing `yarn start`

#### with npm
```
cd SmartHomeSimulatorFront
npm install
```

Start local server by typing `npm start`


### Installation for Back-End

Smart Home Simulator uses java 11 for the backend 

It also uses maven and Spring boot but you don't have to download anything for that part (more details in README.md in SmartHomeSimulatorBack folder

Install jdk 11.0.8 or newer to be able to run the code

IntelliJ as a text editor is perfect but Eclipse or Netbeans can be used as well


### To test the App with Back-End

Start the app with yarn or npm.
Run the project inside the folder SmartHomeSimulatorBack (Folder name: SmartHomeSimulator) using your text editor.

First time running the code of backend? 
	Don't forget to assign the right jdk to be able to run it. (IntelliJ will ask)
	
### If it works

You'll see the Spring logo appear in your console and all the services starting in port 8080.

### FOLLOW THESE STEPS BEFORE STARTING THE SIMULATION

1. Upload Home Layout --> you can use the HomeLayoutExample.json file provided
2. Set Date and time
3. Set Outdoor temperature
4. Set desired empty room temperature
5. Set winter/summer months and temperature
6. Create the users you want and place them wherever you want them at the beginning
7. Select a current simulation user 
8. Add the rooms in zones (NOTE: all the rooms should be in zones except backyard and outside)
9. Start the simulation and enjoy!
