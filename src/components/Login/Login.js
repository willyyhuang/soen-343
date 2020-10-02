import {
  Button, Card, Col, Input, Row,
} from 'antd'
import React from 'react'
import './index.css'

const Login = () => (
  <Row type='flex' align='middle'>
    <Col span={8} />
    <Col span={8}>
      <Card style={{marginTop: '40%'}}>
        <Input placeholder='username' />
        <Input.Password className='row' placeholder='password' />
        <Button className='row' block type='ghost'>Login</Button>
      </Card>
    </Col>
    <Col span={8} />
  </Row>
)

Login.displayName = 'Login'
export default Login
