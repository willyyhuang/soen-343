import {
  Button, Card, Col, Divider, Input, Row, Typography,
} from 'antd'
import React from 'react'
import placeholderLogo from '../../images/placeholderLogo.jpg'
import './index.css'

const Login = () => (
  <Row type='flex' align='middle'>
    <Col span={8} />
    <Col span={8} className='login'>
      <img src={placeholderLogo} className='logo' />
      <Card style={{backgroundColor: '#FBF8F8'}}>
        <Input placeholder='username or email' />
        <Input.Password className='row' placeholder='password' />
        <Button className='row' block style={{backgroundColor: '#5FAEF3', color: 'white'}} type='ghost'>Log In</Button>
      </Card>
      <Divider />
      <Card style={{backgroundColor: '#FBF8F8'}}>
        <Typography.Text>
          Don&apos;t have an account?
        </Typography.Text>
        <Button type='link'>Sign up here!</Button>
      </Card>
    </Col>
    <Col span={8} />
  </Row>
)

Login.displayName = 'Login'
export default Login
