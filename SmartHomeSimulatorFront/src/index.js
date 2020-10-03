/* eslint-disable no-underscore-dangle */
import React from 'react'
import ReactDOM from 'react-dom'
import {createStore} from 'redux'
import {Provider} from 'react-redux'
import Layout from './Layout'
import rootReducer from './models'

const store = createStore(rootReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__
  && window.__REDUX_DEVTOOLS_EXTENSION__())

const App = () => (
  <Provider store={store}>
    <Layout />
  </Provider>
)

ReactDOM.render(
  <App />,
  document.getElementById('root'),
)
