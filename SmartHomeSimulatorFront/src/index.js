import React from 'react'
import ReactDOM from 'react-dom'
import {createStore} from 'redux'
import {Provider} from 'react-redux'
import Router from './Router'
import rootReducer from './models'

const store = createStore(
  rootReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
)

const App = () => (
  <Provider store={store}>
    <Router />
  </Provider>
)

ReactDOM.render(<App />, document.getElementById('root'))
