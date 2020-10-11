import './Router.css'
import {BrowserRouter, Redirect, Route} from 'react-router-dom'
import React, {useEffect} from 'react'
import {connect} from 'react-redux'
import {Dashboard, Login} from './components'

const Router = ({dispatch, authentication}) => {
  useEffect(() => {
    const payload = localStorage.getItem('authentication')
    if (payload) {
      dispatch({type: 'SET_STATE', payload: JSON.parse(payload)})
    }
  }, [])

  useEffect(() => {
    localStorage.setItem('authentication', JSON.stringify(authentication))
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
})

Router.displayName = 'Router'
export default connect(mapStateToProps)(Router)
