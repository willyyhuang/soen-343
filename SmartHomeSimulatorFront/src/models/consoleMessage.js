import produce from 'immer'

const initialState = {
  messages: [],
  simulationTime: null,
}

const consoleMessage = produce((state, action) => {
  const {type, payload} = action
  switch (type) {
    case 'ADD_CONSOLE_MESSAGE':
      state.messages.push(payload)
      break
    case 'SET_SIMULATION_TIME':
      state.simulationTime = payload
      break
    default: return state
  }
}, {...initialState})

export default consoleMessage
