import './Router.css'
import {BrowserRouter, Redirect, Route} from 'react-router-dom'
import React, {useEffect} from 'react'
import {connect} from 'react-redux'
import {Dashboard} from './components'

const Router = ({dispatch, simulationConfig}) => {
  useEffect(() => {
    const sessionConfig = sessionStorage.getItem('simulationConfig')
    if (sessionConfig) {
      dispatch({type: 'SET_SIMULATION_CONFIG_STATE', payload: JSON.parse(sessionConfig)})
    } else {
      dispatch({type: 'RESET_STATE'})
    } // eslint-disable-next-line
  }, [])

  useEffect(() => {
    sessionStorage.setItem('simulationConfig', JSON.stringify(simulationConfig))
  })

  return (
    <BrowserRouter>
      <Route path='/dashboard' component={Dashboard} />
      <Route
        path='/*'>
        <Redirect to='/dashboard' />
        )
      </Route>
    </BrowserRouter>
  )
}

const mapStateToProps = (state) => ({
  simulationConfig: state.simulationConfig,
})

Router.displayName = 'Router'
export default connect(mapStateToProps)(Router)
