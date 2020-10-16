import axios from 'axios'
import appsettings from '../appsettings.json'

// API routes
export const addSimulationProfile = `${appsettings.baseApiUrl}/api/v1/user/add`
export const editName = `${appsettings.baseApiUrl}/api/v1/user/edit/:name`
export const editLocation = `${appsettings.baseApiUrl}/api/v1/user/editHomeLocation`
export const removeSimulationProfile = `${appsettings.baseApiUrl}/api/v1/user/remove/:name`

export const setSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/setCurrentSimulationProfile`
export const getSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/get`

// User Profile
export async function getProfile() {
  return axios.get(getSimulationProfile)
}

export async function addProfile(payload) {
  return axios({
    method: 'POST',
    url: addSimulationProfile,
    data: payload,
  })
}

export async function editProfileName(payload) {
  return axios({
    method: 'POST',
    url: editName.replace(':name', payload),
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

export async function setCurrentProfile(payload) {
  return axios({
    method: 'POST',
    url: setSimulationProfile,
    params: {name: payload},
  })
}
