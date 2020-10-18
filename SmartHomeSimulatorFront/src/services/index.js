import axios from 'axios'
import appsettings from '../appsettings.json'

// API routes

// Simulation Profile
export const addSimulationProfile = `${appsettings.baseApiUrl}/api/v1/user/add`
export const editName = `${appsettings.baseApiUrl}/api/v1/user/edit/:name`
export const editLocation = `${appsettings.baseApiUrl}/api/v1/user/editHomeLocation`
export const removeSimulationProfile = `${appsettings.baseApiUrl}/api/v1/user/remove/:name`
export const setSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/setCurrentSimulationUser`
export const getSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/get`
export const loadLayout = `${appsettings.baseApiUrl}/api/v1/simulation/loadLayout`
export const setDate = `${appsettings.baseApiUrl}/api/v1/simulation/setDate`
export const setTime = `${appsettings.baseApiUrl}/api/v1/simulation/setTime`
export const setInsideTemp = `${appsettings.baseApiUrl}/api/v1/simulation/setInsideTemp`
export const setOutsideTemp = `${appsettings.baseApiUrl}/api/v1/simulation/setOutsideTemp`
export const startSimulation = `${appsettings.baseApiUrl}/api/v1/simulation/start`
export const stopSimulation = `${appsettings.baseApiUrl}/api/v1/simulation/stop`
export const blockWindow = `${appsettings.baseApiUrl}/api/v1/simulation/blockWindow`
export const unblockWindow = `${appsettings.baseApiUrl}/api/v1/simulation/unblockWindow`

export async function block(payload) {
  return axios({
    method: 'POST',
    url: blockWindow,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function unblock(payload) {
  return axios({
    method: 'POST',
    url: unblockWindow,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function start() {
  return axios({
    method: 'POST',
    url: startSimulation,
  })
}

export async function stop() {
  return axios({
    method: 'POST',
    url: stopSimulation,
  })
}

export async function setSimulationDate(payload) {
  return axios({
    method: 'POST',
    url: setDate,
    params: {date: payload},
  })
}

export async function setSimulationTime(payload) {
  return axios({
    method: 'POST',
    url: setTime,
    params: {time: payload},
  })
}

export async function setSimulationInsideTemp(payload) {
  return axios({
    method: 'POST',
    url: setInsideTemp,
    params: {insideTemp: payload},
  })
}

export async function setSimulationOutsideTemp(payload) {
  return axios({
    method: 'POST',
    url: setOutsideTemp,
    params: {outsideTemp: payload},
  })
}

export async function uploadLayout(payload) {
  return axios({
    method: 'POST',
    url: loadLayout,
    data: {roomList: payload},
  })
}

// User Profile
export async function getProfile() {
  return axios.get(getSimulationProfile)
}

export async function setCurrentProfile(payload) {
  return axios({
    method: 'POST',
    url: setSimulationProfile,
    params: {name: payload},
  })
}

export async function addProfile(payload) {
  return axios({
    method: 'POST',
    url: addSimulationProfile,
    data: payload,
  })
}

export async function editProfile(payload) {
  return axios({
    method: 'POST',
    url: payload.type === 'editLocation' ? editLocation : editName.replace(':name', payload.name),
    params: payload.type === 'editLocation' ? {homeLocation: payload.homeLocation, name: payload.name} : {
      newName: payload.newName,
    },
  })
}

export async function deleteProfile(payload) {
  return axios({
    method: 'POST',
    url: removeSimulationProfile.replace(':name', payload),
  })
}
