import produce from 'immer'

const initialState = []

const zone = produce(
  (state, action) => {
    const {type, payload} = action
    switch (type) {
      case 'SET_ZONE':
        state = payload
        return state
      default:
        return state
    }
  },
  {...initialState},
)

export default zone
