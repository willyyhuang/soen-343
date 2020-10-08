import produce from 'immer'

const initialState = {
  user: {},
}

const user = produce((state, action) => {
  const {type, payload} = action
  switch (type) {
    case 'SET_USER':
      state.user = payload
      return state
    default: return state
  }
}, {...initialState})

export default user
