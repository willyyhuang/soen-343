import './Router.css'
import {BrowserRouter, Redirect, Route} from 'react-router-dom'
import React, {useEffect} from 'react'
import {connect} from 'react-redux'
import {Dashboard, Login} from './components'

const Router = ({dispatch, authentication, simulationConfig}) => {
  useEffect(() => {
    const sessionAuthentication = sessionStorage.getItem('authentication')
    const sessionConfig = sessionStorage.getItem('simulationConfig')
    if (sessionAuthentication) {
      dispatch({type: 'SET_STATE', payload: JSON.parse(sessionAuthentication)})
    } else {
      dispatch({type: 'RESET_STATE'})
    }
    if (sessionConfig) {
      dispatch({type: 'SET_SIMULATION_CONFIG_STATE', payload: JSON.parse(sessionConfig)})
    } else {
      dispatch({type: 'RESET_STATE'})
    }
  }, [])

  useEffect(() => {
    sessionStorage.setItem('authentication', JSON.stringify(authentication))
    sessionStorage.setItem('simulationConfig', JSON.stringify(simulationConfig))
  })

  const {isLoggedIn} = authentication
  return (
    <BrowserRouter>
      <Route path='/login' component={Login} />
      <Route path='/dashboard' component={Dashboard} />
      <Route
        path='/*'>
        {(isLoggedIn ? <Redirect to='/dashboard' /> : <Redirect to='/login' />)}
      </Route>
    </BrowserRouter>
  )
}

const mapStateToProps = (state) => ({
  authentication: state.authentication,
  simulationConfig: state.simulationConfig,
})

Router.displayName = 'Router'
export default connect(mapStateToProps)(Router)
