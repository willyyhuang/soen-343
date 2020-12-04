import axios from 'axios'
import appsettings from '../appsettings.json'

// Objects
export const blockWindow = `${appsettings.baseApiUrl}/api/v1/simulation/blockWindow`
export const unblockWindow = `${appsettings.baseApiUrl}/api/v1/simulation/unblockWindow`
export const onLight = `${appsettings.baseApiUrl}/api/v1/simulation/onLight`
export const offLight = `${appsettings.baseApiUrl}/api/v1/simulation/offLight`
export const openWindow = `${appsettings.baseApiUrl}/api/v1/simulation/openWindow`
export const closeWindow = `${appsettings.baseApiUrl}/api/v1/simulation/closeWindow`
export const openDoor = `${appsettings.baseApiUrl}/api/v1/simulation/openDoor`
export const closeDoor = `${appsettings.baseApiUrl}/api/v1/simulation/closeDoor`

export async function openRoomDoor(payload) {
  return axios({
    method: 'POST',
    url: openDoor,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function closeRoomDoor(payload) {
  return axios({
    method: 'POST',
    url: closeDoor,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function openRoomWindow(payload) {
  return axios({
    method: 'POST',
    url: openWindow,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function closeRoomWindow(payload) {
  return axios({
    method: 'POST',
    url: closeWindow,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function turnOnLight(payload) {
  return axios({
    method: 'POST',
    url: onLight,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

export async function turnOffLight(payload) {
  return axios({
    method: 'POST',
    url: offLight,
    params: {
      objectID: payload.id,
      roomName: payload.name,
    },
  })
}

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

// Mode
export const startSimulation = `${appsettings.baseApiUrl}/api/v1/simulation/start`
export const stopSimulation = `${appsettings.baseApiUrl}/api/v1/simulation/stop`
export const autoMode = `${appsettings.baseApiUrl}/api/v1/simulation/autoMode`
export const awayMode = `${appsettings.baseApiUrl}/api/v1/simulation/awayMode`
export const timeBeforeAuthority = `${appsettings.baseApiUrl}/api/v1/simulation/awayMode/timeBeforeAuthorities`
export const awayModeLight = `${appsettings.baseApiUrl}/api/v1/simulation/awayMode/setLights`
export const timeToKeepLightOn = `${appsettings.baseApiUrl}/api/v1/simulation/awayMode/setTimeToKeepLightsOn`
export const awayModeLightStatus = `${appsettings.baseApiUrl}/api/v1/simulation/awayMode/turnOnOffLights`

export async function setAwayModeLightStatus(payload) {
  return axios({
    method: 'PUT',
    url: awayModeLightStatus,
    params: {
      status: payload,
    },
  })
}

export async function setLights(payload) {
  return axios({
    method: 'PUT',
    url: awayModeLight,
    data: payload,
  })
}

export async function setTimeToKeepLightOn(payload) {
  return axios({
    method: 'PUT',
    url: timeToKeepLightOn,
    params: {
      timeToKeepLightsOn: payload,
    },
  })
}

export async function setTimeBeforeAuthority(payload) {
  return axios({
    method: 'PUT',
    url: timeBeforeAuthority,
    params: {
      timeBeforeAuthorities: payload,
    },
  })
}

export async function setAwayMode(payload) {
  return axios({
    method: 'PUT',
    url: awayMode,
    params: {
      awayMode: payload,
    },
  })
}

export async function setAutoMode(payload) {
  return axios({
    method: 'POST',
    url: autoMode,
    params: {
      autoMode: payload,
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

// Simulation Parameter
export const setDate = `${appsettings.baseApiUrl}/api/v1/simulation/setDate`
export const setTime = `${appsettings.baseApiUrl}/api/v1/simulation/setTime`
export const setOutsideTemp = `${appsettings.baseApiUrl}/api/v1/simulation/setOutsideTemp`
export const loadLayout = `${appsettings.baseApiUrl}/api/v1/simulation/loadLayout`
export const summerMonths = `${appsettings.baseApiUrl}/api/v1/simulation/setSummerMonths`
export const summerTemp = `${appsettings.baseApiUrl}/api/v1/simulation/setSummerTemperature`
export const winterMonths = `${appsettings.baseApiUrl}/api/v1/simulation/setWinterMonths`
export const winterTemp = `${appsettings.baseApiUrl}/api/v1/simulation/setWinterTemperature`

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

export async function setSummerMonths(payload) {
  return axios({
    method: 'POST',
    url: summerMonths,
    params: {months: payload},
  })
}

export async function setSummerTemp(payload) {
  return axios({
    method: 'POST',
    url: summerTemp,
    params: {temperature: payload},
  })
}

export async function setWinterMonths(payload) {
  return axios({
    method: 'POST',
    url: winterMonths,
    params: {months: payload},
  })
}

export async function setWinterTemp(payload) {
  return axios({
    method: 'POST',
    url: winterTemp,
    params: {temperature: payload},
  })
}

// User Profile
export const addSimulationProfile = `${appsettings.baseApiUrl}/api/v1/user/add`
export const editName = `${appsettings.baseApiUrl}/api/v1/user/edit/:name`
export const editLocation = `${appsettings.baseApiUrl}/api/v1/user/editHomeLocation`
export const removeSimulationProfile = `${appsettings.baseApiUrl}/api/v1/user/remove/:name`
export const setSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/setCurrentSimulationUser`
export const getSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/get`

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
    url:
      payload.type === 'editLocation'
        ? editLocation
        : editName.replace(':name', payload.name),
    params:
      payload.type === 'editLocation'
        ? {homeLocation: payload.homeLocation, name: payload.name}
        : {
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

// Smart Home Heating

export const zone = `${appsettings.baseApiUrl}/api/v1/simulation/createZone`

export async function createZone(payload) {
  return axios({
    url: zone,
    method: 'POST',
    data: payload,
  })
}
