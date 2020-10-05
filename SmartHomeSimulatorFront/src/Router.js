import {BrowserRouter, Redirect, Route} from 'react-router-dom'
import React from 'react'
import './Router.css'
import {connect} from 'react-redux'
import {Login} from './components'

const Router = ({authentication}) => {
  const {isLoggedIn} = authentication
  return (
    <BrowserRouter>
      <Route path='/login' component={Login} />
      <Route path='/dashboard' component={null} />
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
