import axios from 'axios'
import appsettings from '../appsettings.json'

// API routes
export const userSignUp = `${appsettings.baseApiUrl}/api/v1/user/register`
export const userLogin = ''

export async function signUp(payload) {
  return axios.post(userSignUp, payload)
}

export async function logIn(payload) {
  return axios.post(userLogin, payload)
}
