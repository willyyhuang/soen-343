import React, {useState} from 'react'
import {
  Dropdown, Menu, Table, Modal, Row, Form, Input, Card, Button, Divider, Select,
} from 'antd'
import {DownOutlined} from '@ant-design/icons'
import {
  addProfile, editProfile, deleteProfile, setCurrentProfile,
} from '../../services'

const SimulationProfileCard = ({simulationConfig, authentication, fetchUserProfiles}) => {
  const {username} = authentication
  const ADD_PROFILE_DATA_INITIAL_STATE = {
    name: '',
    role: '',
    homeLocation: '',
  }
  const [addProfileFormData, setAddProfileFormData] = useState(ADD_PROFILE_DATA_INITIAL_STATE)
  const [isAddUserModalVisible, setIsAddUserModalVisible] = useState(false)
  const [editUserModalProps, setEditUserModalProps] = useState({
    name: '',
    visible: false,
  })
  const [homeLocation, setHomeLocation] = useState()

  const columns = [{
    title: 'Name',
    dataIndex: 'name',
  },
  {
    title: 'Role',
    dataIndex: 'role',
  },
  {
    title: 'Home Location',
    dataIndex: 'homeLocation',
  },
  {
    title: 'Actions',
    render: (data) => {
      const menu = (
        <Menu onClick={(value) => {
          if (value.key === 'edit') {
            setEditUserModalProps({name: data.name, visible: true})
          } else if (value.key === 'delete') {
            deleteProfile({username, name: data.name}).then((response) => {
              if (response.data) {
                fetchUserProfiles()
              }
            })
          }
        }}>
          <Menu.Item key='edit'>Edit Home Location</Menu.Item>
          <Menu.Item key='delete'>Delete</Menu.Item>
        </Menu>
      )
      return <Dropdown overlay={menu} trigger={['click']}>
        <DownOutlined />
      </Dropdown>
    },
  }]

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
        setIsAddUserModalVisible(false)
      }}
      title='Add Profile Modal'
      onCancel={() => {
        setAddProfileFormData(ADD_PROFILE_DATA_INITIAL_STATE)
        setIsAddUserModalVisible(false)
      }}
      visible={isAddUserModalVisible}>
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

  const editProfileModal = (
    <Modal
      closable={false}
      onOk={() => {
        editProfile({username, homeLocation, name: editUserModalProps.name}).then((response) => {
          if (response.data) {
            fetchUserProfiles()
          }
        })
        setEditUserModalProps({visible: false})
      }}
      onCancel={() => setEditUserModalProps({visible: false})}
      visible={editUserModalProps.visible}>
      <Form.Item label='Home Location'>
        <Input onChange={(e) => setHomeLocation(e.target.value)} placeholder='select a room' />
      </Form.Item>
    </Modal>
  )

  const profileSelect = (
    <Form.Item label='Current Logged In Profile'>
      <Select
        onSelect={(value) => {
          setCurrentProfile({username, name: value}).then((response) => {
            if (response.data) {
              fetchUserProfiles()
            }
          })
        }}
        value={simulationConfig.currentSimulationProfile && simulationConfig.currentSimulationProfile.name}
        placeholder='No Profile Selected'>
        {simulationConfig.simulationProfiles.map((item) => <Select.Option key={item.name}>{item.name}</Select.Option>)}
      </Select>
    </Form.Item>
  )

  return (
    <Card title='Simulation Profiles'>
      {editProfileModal}
      {addProfileModal}
      {profileSelect}
      <Row>
        <Button onClick={() => setIsAddUserModalVisible(true)}>Add Profile</Button>
      </Row>
      <Divider />
      <Table pagination={false} dataSource={simulationConfig.simulationProfiles} columns={columns} />
    </Card>
  )
}

export default SimulationProfileCard
