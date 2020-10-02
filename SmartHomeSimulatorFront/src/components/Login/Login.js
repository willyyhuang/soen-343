import {
  Button, Card, Col, Divider, Input, message, Row, Typography,
} from 'antd'
import React, {useState} from 'react'
import {logIn, signUp} from '../../services'
import placeholderLogo from '../../images/placeholderLogo.jpg'
import './index.css'

const Login = () => {
  const [isSignUpPage, setIsSignUpPage] = useState(false)
  const [signUpPayload, setSignUpPayload] = useState({
    username: undefined,
    password: undefined,
  })
  const [signInPayload, setSignInPayload] = useState({
    username: undefined,
    password: undefined,
  })
  const [confirmPassword, setConfirmPassword] = useState()

  const SignUpCard = (
    <Card className='card'>
      <Input
        onChange={(e) => setSignUpPayload({
          username: e.target.value,
          password: signUpPayload.password,
        })}
        placeholder='username or email'
        value={signUpPayload.username} />
      <Input.Password
        onChange={(e) => setSignUpPayload({
          username: signUpPayload.username,
          password: e.target.value,
        })}
        className='row'
        placeholder='password'
        value={signUpPayload.password} />
      <Input.Password
        onChange={(e) => setConfirmPassword(e.target.value)}
        className='row'
        disabled={!signUpPayload.password}
        placeholder='confirm password'
        value={confirmPassword} />
      <Button
        onClick={() => {
          if (confirmPassword !== signUpPayload.password) {
            message.error('password does not match')
          } else {
            signUp(signUpPayload)
          }
        }}
        className='button'
        block
        type='ghost'>
        Sign Up
      </Button>
      <Button className='row' block onClick={() => setIsSignUpPage(false)} type='link'>Back to sign in page</Button>
    </Card>
  )

  const SignInCard = (
    <>
      <Card className='card'>
        <Input
          onChange={(e) => setSignInPayload({
            username: e.target.value,
            password: signInPayload.password,
          })}
          placeholder='username or email' />
        <Input.Password
          onChange={(e) => setSignInPayload({
            username: signInPayload.username,
            password: e.target.value,
          })}
          className='row'
          placeholder='password' />
        <Button onClick={() => logIn(signInPayload)} className='button' block type='ghost'>Log In</Button>
      </Card>
      <Divider />
      <Card className='card'>
        <Typography.Text>
          Don&apos;t have an account?
        </Typography.Text>
        <Button onClick={() => setIsSignUpPage(true)} type='link'>Sign up here!</Button>
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

Login.displayName = 'Login'
export default Login
