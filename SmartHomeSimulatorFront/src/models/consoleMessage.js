import produce from 'immer'

const initialState = {
  messages: [],
}

const consoleMessage = produce(
  (state, action) => {
    const {type, payload} = action
    switch (type) {
      case 'ADD_CONSOLE_MESSAGE':
        state.messages.push(payload)
        break
      default:
        return state
    }
  },
  {...initialState},
)

export default consoleMessage
