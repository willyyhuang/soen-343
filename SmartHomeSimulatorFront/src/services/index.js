import axios from 'axios'
import appsettings from '../appsettings.json'

// API routes
export const userSignUp = `${appsettings.baseApiUrl}/api/v1/user/register`
export const userLogin = `${appsettings.baseApiUrl}/api/v1/user/login`
export const addSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/add`
export const removeSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/remove`
export const editSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/editHomeLocation`
export const setSimulationProfile = `${appsettings.baseApiUrl}/api/v1/simulation/editCurrentSimulationProfile`
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
