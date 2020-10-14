import {
  Button,
  Card,
  Col,
  DatePicker,
  Divider,
  Form,
  Input,
  InputNumber,
  Layout,
  Modal,
  Row,
  Switch,
  Table,
  TimePicker,
  Typography,
  Upload,
} from 'antd'
import React, {useState} from 'react'
import {connect} from 'react-redux'
import './Dashboard.css'
import {addProfile, getProfile} from '../../services'

const Dashboard = ({simulationConfig, authentication, dispatch}) => {
  const fetchUserProfiles = () => {
    getProfile({username: authentication.username}).then((response) => {
      const {data} = response
      const {currentSimulatorProfiles, simulationProfiles} = data
      dispatch({type: 'SET_SIMULATION_PROFILES', payload: simulationProfiles})
      dispatch({type: 'SET_CURRENT_SIMULATION_PROFILE', payload: currentSimulatorProfiles})
    })
  }
  const ADD_PROFILE_DATA_INITIAL_STATE = {
    name: '',
    role: '',
    homeLocation: '',
  }
  const [addProfileFormData, setAddProfileFormData] = useState(ADD_PROFILE_DATA_INITIAL_STATE)
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
      <Form.Item label='Date'>
        <DatePicker placeholder='enter a date' />
      </Form.Item>
      <Form.Item label='Time'>
        <TimePicker placeholder='enter a time' />
      </Form.Item>
    </Card>
  )

  const columns = [{
    title: 'Name',
    dataIndex: 'name',
  },
  {
    title: 'Role',
    dataIndex: 'role',
  }, {
    title: 'Home Location',
    dataIndex: 'homeLocation',
  }]

  const profileCard = (
    <Card title='Simulation Profiles'>
      <Button onClick={() => setIsModalVisible(true)}>Add Profile</Button>
      <Divider />
      <Table pagination={false} dataSource={simulationConfig.simulationProfiles} columns={columns} />
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

  const addProfileModal = (
    <Modal
      okText='add'
      onOk={() => {
        addProfile({
          username: authentication.username,
          ...addProfileFormData,
        }).then((response) => {
          if (response.data) {
            fetchUserProfiles()
          }
        })
        setAddProfileFormData(ADD_PROFILE_DATA_INITIAL_STATE)
        setIsModalVisible(false)
      }}
      title='Add Profile Modal'
      onCancel={() => {
        setAddProfileFormData(ADD_PROFILE_DATA_INITIAL_STATE)
        setIsModalVisible(false)
      }}
      visible={isModalVisible}>
      <Form.Item label='Name'>
        <Input value={addProfileFormData.name} onChange={(e) => setAddProfileFormData({name: e.target.value, role: addProfileFormData.role, homeLocation: addProfileFormData.homeLocation})} placeholder='enter a name' />
      </Form.Item>
      <Form.Item label='Role'>
        <Input value={addProfileFormData.role} onChange={(e) => setAddProfileFormData({name: addProfileFormData.name, role: e.target.value, homeLocation: addProfileFormData.homeLocation})} placeholder='enter a role' />
      </Form.Item>
      <Form.Item label='Home Location'>
        <Input value={addProfileFormData.homeLocation} onChange={(e) => setAddProfileFormData({name: addProfileFormData.name, role: addProfileFormData.role, homeLocation: e.target.value})} placeholder='enter a home location' />
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
          {addProfileModal}
          <Col span={8} />
          <Col span={8}>
            {parameterCard}
            <Divider />
            {profileCard}
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
  authentication: state.authentication,
  simulationConfig: state.simulationConfig,
})

Dashboard.displayName = 'Dashboard'
export default connect(mapStateToProps)(Dashboard)
