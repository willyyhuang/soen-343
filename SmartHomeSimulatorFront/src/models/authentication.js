import produce from 'immer'
import {logIn} from '../services'

const initialState = {
  username: '',
  password: '',
  confirmPassword: '',
  isLoggedIn: false,
}

const authentication = produce((state, action) => {
  const {type, payload} = action
  switch (type) {
    case 'SET_USERNAME':
      state.username = payload
      return state
    case 'SET_PASSWORD':
      state.password = payload
      return state
    case 'SET_CONFIRM_PASSWORD':
      state.confirmPassword = payload
      return state
    case 'RESET_STATE':
      state = initialState
      return state
    case 'LOGIN':
      logIn({
        username: state.username,
        password: state.password,
      }).then((response) => {
        if (response.success) {
          state.isLoggedIn = true
        }
      })
      return state
    default: return state
  }
}, {...initialState})

export default authentication
