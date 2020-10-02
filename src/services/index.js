import axios from 'axios'

// API routes
export const userSignUp = 'localhost:8080/api/v1/user'
export const userLogin = ''

export async function signUp(payload) {
  return axios.post(userSignUp, payload)
}

export async function logIn(payload) {
  return axios.post(userLogin, payload)
}
