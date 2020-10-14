import axios from 'axios'
import appsettings from '../appsettings.json'

// API routes
export const userSignUp = `${appsettings.baseApiUrl}/api/v1/user/register`
export const userLogin = `${appsettings.baseApiUrl}/api/v1/user/login`
export const addSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/add`
export const removeSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/remove`
export const editSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/editHomeLocation`
export const setSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/setCurrentSimulationProfile`
export const getSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/simulationConfig`

export async function signUp(payload) {
  return axios.post(userSignUp, payload)
}

export async function logIn(payload) {
  return axios.post(userLogin, payload)
}

export async function addProfile(payload) {
  return axios({
    method: 'POST',
    url: addSimulationProfile,
    params: {username: payload.username},
    data: payload,
  })
}

export async function getProfile(payload) {
  return axios.get(getSimulationProfile, {params: payload})
}

export async function editProfile(payload) {
  return axios({
    method: 'POST',
    url: editSimulationProfile,
    params: {username: payload.username, homeLocation: payload.homeLocation, name: payload.name},
  })
}

export async function deleteProfile(payload) {
  return axios({
    method: 'POST',
    url: removeSimulationProfile,
    params: {username: payload.username, name: payload.name},
  })
}

export async function setCurrentProfile(payload) {
  return axios({
    method: 'POST',
    url: setSimulationProfile,
    params: {username: payload.username, name: payload.name},
  })
}
