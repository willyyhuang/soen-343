import React, {useState} from 'react'
import {
  Dropdown, Menu, Table, Modal, Row, Form, Input, Card, Button, Divider, Select,
} from 'antd'
import {DownOutlined} from '@ant-design/icons'
import {
  addProfile, editProfile, deleteProfile, setCurrentProfile,
} from '../../services'

const ADD_PROFILE_DATA_INITIAL_STATE = {
  name: '',
  role: '',
  homeLocation: '',
}
const EDIT_PROFILE_DATA_INITIAL_STATE = {
  name: '',
  visible: false,
  type: '',
  homeLocation: '',
  newName: '',
}

const SimulationProfileCard = ({simulationConfig, fetchUserProfiles}) => {
  const {simulationUsers, currentSimulationProfile} = simulationConfig

  const [addProfileFormData, setAddProfileFormData] = useState(ADD_PROFILE_DATA_INITIAL_STATE)
  const [isAddUserModalVisible, setIsAddUserModalVisible] = useState(false)
  const [editProfileFormData, setEditProfileFormData] = useState(EDIT_PROFILE_DATA_INITIAL_STATE)
  const [isEditUserModalVisible, setIsEditUserModalVisible] = useState(false)

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
          if (value.key === 'editLocation') {
            setEditProfileFormData({name: data.name, type: value.key})
            setIsEditUserModalVisible(true)
          } else if (value.key === 'editName') {
            setEditProfileFormData({name: data.name, type: value.key})
            setIsEditUserModalVisible(true)
          } else if (value.key === 'delete') {
            deleteProfile(data.name).then((response) => {
              if (response.data) {
                fetchUserProfiles()
              }
            })
          }
        }}>
          <Menu.Item key='editLocation'>Edit Home Location</Menu.Item>
          <Menu.Item key='editName'>Edit Name</Menu.Item>
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
        addProfile(addProfileFormData).then((response) => {
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
        editProfile(editProfileFormData).then((response) => {
          if (response.data) {
            fetchUserProfiles()
          }
        })
        setIsEditUserModalVisible(false)
        setEditProfileFormData(EDIT_PROFILE_DATA_INITIAL_STATE)
      }}
      onCancel={() => setEditProfileFormData(EDIT_PROFILE_DATA_INITIAL_STATE)}
      visible={isEditUserModalVisible}>
      {editProfileFormData.type === 'editLocation' ? <Form.Item label='Home Location'>
        <Input
          value={editProfileFormData.homeLocation}
          onChange={(e) => setEditProfileFormData({
            type: editProfileFormData.type,
            name: editProfileFormData.name,
            homeLocation: e.target.value,
          })}
          placeholder='select a room' />
      </Form.Item> : <Form.Item label='New Name'>
        <Input
          value={editProfileFormData.newName}
          onChange={(e) => setEditProfileFormData({
            type: editProfileFormData.type,
            name: editProfileFormData.name,
            newName: e.target.value,
          })}
          placeholder='enter a new name' />
      </Form.Item>}
    </Modal>
  )

  const profileSelect = (
    <Form.Item label='Current Logged In Profile'>
      <Select
        onSelect={(value) => {
          setCurrentProfile(value).then((response) => {
            if (response.data) {
              fetchUserProfiles()
            }
          })
        }}
        value={currentSimulationProfile && currentSimulationProfile.name}
        placeholder='No Profile Selected'>
        {simulationUsers && simulationUsers.map((item) => <Select.Option key={item.name}>{item.name}</Select.Option>)}
      </Select>
    </Form.Item>
  )

  return (
    <Card title='Simulation Profiles'>
      {addProfileModal}
      {editProfileModal}
      {profileSelect}
      <Row>
        <Button onClick={() => setIsAddUserModalVisible(true)}>Add Profile</Button>
      </Row>
      <Divider />
      <Table pagination={false} dataSource={simulationUsers} columns={columns} />
    </Card>
  )
}

export default SimulationProfileCard
