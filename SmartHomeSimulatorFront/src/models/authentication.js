import produce from 'immer'

const initialState = {
  username: '',
  password: '',
  confirmPassword: '',
}

const authentication = produce((state = initialState, action) => {
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
    default: return state
  }
}, {})

export default authentication
