import {
  Button, Card, Col, Divider, Form, Input, InputNumber, Layout, Modal, Row, Switch, Typography, Upload,
} from 'antd'
import React, {useState} from 'react'
import {connect} from 'react-redux'
import './Dashboard.css'

const Dashboard = ({dispatch}) => {
  const [isModalVisible, setIsModalVisible] = useState(false)
  const parameterCard = (
    <Card
      extra={<Upload>
        <Button>Upload House Layout File</Button>
      </Upload>}
      actions={[<Button>Save</Button>]}
      title='Set up simulation parameter'>
      <Form.Item label='Temperature Indoor (°C)'>
        <InputNumber />
      </Form.Item>
      <Form.Item label='Temperature Outdoor (°C)'>
        <InputNumber />
      </Form.Item>
      <Form.Item label='Time'>
        <Input placeholder='enter a time' />
      </Form.Item>
    </Card>
  )

  const userCard = (
    <Card title='Simulation Users'>
      <Button onClick={() => setIsModalVisible(true)}>Add User</Button>
    </Card>
  )

  const simulationSwitchCard = (
    <Card>
      <Row>
        <Col span={8}>
          <Switch className='item' />
          <Typography.Text>Simulation Mode</Typography.Text>
        </Col>
      </Row>
    </Card>
  )

  const addUserModal = (
    <Modal title='Add User Modal' onCancel={() => setIsModalVisible(false)} visible={isModalVisible}>
      <Form.Item label='Username'>
        <Input placeholder='enter an username' />
      </Form.Item>
      <Form.Item label='Password'>
        <Input placeholder='enter a password' />
      </Form.Item>
      <Form.Item label='Home Location'>
        <Input placeholder='enter a home location' />
      </Form.Item>
    </Modal>
  )

  return (
    <Layout className='layout'>
      <Layout.Header>
        <Row>
          <Col push={23}>
            <Button onClick={() => dispatch({type: 'RESET_STATE'})}>Log Out</Button>
          </Col>
        </Row>
      </Layout.Header>
      <Layout.Content className='content'>
        <Row type='flex' align='middle'>
          {addUserModal}
          <Col span={8} />
          <Col span={8}>
            {parameterCard}
            <Divider />
            {userCard}
            <Divider />
            {simulationSwitchCard}
          </Col>
          <Col span={8} />
        </Row>
      </Layout.Content>
    </Layout>
  )
}

const mapStateToProps = (state) => ({
  user: state.user,
})

Dashboard.displayName = 'Dashboard'
export default connect(mapStateToProps)(Dashboard)
