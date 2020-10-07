import './index.css'
import {
  Button, Card, Col, Divider, Input, message, Row, Typography,
} from 'antd'
import React, {useState} from 'react'
import {connect} from 'react-redux'
import placeholderLogo from '../../images/placeholderLogo.jpg'
import {logIn, signUp} from '../../services'

const Login = ({authentication, dispatch}) => {
  const {username, password, confirmPassword} = authentication
  const [isSignUpPage, setIsSignUpPage] = useState(false)

  const setUserName = (text) => {
    dispatch({type: 'SET_USERNAME', payload: text})
  }
  const setPassword = (text) => {
    dispatch({type: 'SET_PASSWORD', payload: text})
  }
  const setConfirmPassword = (text) => {
    dispatch({type: 'SET_CONFIRM_PASSWORD', payload: text})
  }
  const login = () => {
    logIn({
      username, password,
    }).then((response) => {
      if (response.data) {
        console.log('login success')
      }
    })
  }

  const SignUpCard = (
    <Card className='card'>
      <Input
        onChange={(e) => setUserName(e.target.value)}
        placeholder='username or email'
        value={username} />
      <Input.Password
        onChange={(e) => setPassword(e.target.value)}
        className='row'
        placeholder='password'
        value={password} />
      <Input.Password
        onChange={(e) => setConfirmPassword(e.target.value)}
        className='row'
        disabled={!password}
        placeholder='confirm password'
        value={confirmPassword} />
      <Button
        onClick={() => {
          if (confirmPassword !== password) {
            message.error('password does not match')
          } else if (!username || !password) {
            message.error('missing username or password')
          } else {
            signUp({
              username, password,
            }).then((response) => {
              if (response.data) {
                login()
              }
            })
          }
        }}
        className='button'
        block
        type='ghost'>
        Sign Up
      </Button>
      <Button
        className='row'
        block
        onClick={() => {
          dispatch({type: 'RESET_STATE'})
          setIsSignUpPage(false)
        }}
        type='link'>
        Back to sign in page
      </Button>
    </Card>
  )

  const SignInCard = (
    <>
      <Card className='card'>
        <Input
          onChange={(e) => setUserName(e.target.value)}
          placeholder='username or email' />
        <Input.Password
          onChange={(e) => setPassword(e.target.value)}
          className='row'
          placeholder='password' />
        <Button
          onClick={() => {
            if (!username || !password) {
              message.error('missing username or password')
            } else {
              login()
            }
          }}
          className='button'
          block
          type='ghost'>
          Log In
        </Button>
      </Card>
      <Divider />
      <Card className='card'>
        <Typography.Text>
          Don&apos;t have an account?
        </Typography.Text>
        <Button
          onClick={() => {
            dispatch({type: 'RESET_STATE'})
            setIsSignUpPage(true)
          }}
          type='link'>
          Sign up here!
        </Button>
      </Card>
    </>
  )

  return (
    <Row type='flex' align='middle'>
      <Col span={8} />
      <Col span={8} className='login'>
        <img src={placeholderLogo} className='logo' alt='placeholderLogo' />
        {isSignUpPage ? SignUpCard : SignInCard}
      </Col>
      <Col span={8} />
    </Row>
  )
}

const mapStateToProps = (state) => ({
  authentication: state.authentication,
})

Login.displayName = 'Login'
export default connect(mapStateToProps)(Login)
