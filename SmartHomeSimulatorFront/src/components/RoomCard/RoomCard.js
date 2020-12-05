import {UserOutlined} from '@ant-design/icons'
import {
Button, Card, Form, InputNumber, Popover, Modal, Row, Tag, Typography,
} from 'antd'
import React, {useState} from 'react'
import {ObjectIcon} from '../index'
import {overrideRoomTemp} from '../../services'

const RoomCard = ({
addConsoleMessage, users, room, fetchUserProfiles,
}) => {
  const {
currentTemp, zone, name, objects, overridden,
} = room

  const [isCurrentTempDisplayed, setIsCurrentTempDisplayed] = useState(false)
  const [isOverrideModalVisible, setIsOverrideModalVisible] = useState(false)
  const [newRoomTemp, setNewRoomTemp] = useState()
  const overrideTempModal = (
    <Modal
      visible={isOverrideModalVisible}
      onCancel={() => {
      setNewRoomTemp(null)
      setIsOverrideModalVisible(false)
      }}
      onOk={() => {
        overrideRoomTemp({newTemp: newRoomTemp, roomName: name}).then((response) => {
          const {data} = response
          if (data.success) {
            addConsoleMessage(data.consoleMessage)
            fetchUserProfiles()
          }
        })
        setNewRoomTemp(null)
        setIsOverrideModalVisible(false)
      }}>
      <Form.Item label='New Temperature'>
        <InputNumber onChange={(value) => setNewRoomTemp(value)} value={newRoomTemp} />
      </Form.Item>
    </Modal>
  )
  return (
    <Card
      extra={users.map((user) => (
        <Popover
          placement='bottom'
          content={<Typography.Text>{user.name}</Typography.Text>}>
          <UserOutlined />
        </Popover>
      ))}
      title={<Typography.Text>
        {name}
        {zone && <Tag color='lime' style={{marginLeft: 5}}>{zone}</Tag>}
        {overridden && <Tag color='red' style={{marginLeft: 5}}>Overridden</Tag>}
      </Typography.Text>}>
      {overrideTempModal}
      <Row>
        {isCurrentTempDisplayed
          ? <Typography.Text onClick={() => setIsCurrentTempDisplayed(false)}>
            {`Current Temperature: ${currentTemp}`}
          </Typography.Text>
        : <Button style={{marginBottom: 5}} type='link' onClick={() => setIsCurrentTempDisplayed(true)}>Display Temperature</Button>}
      </Row>
      <Row>
        <Button onClick={() => setIsOverrideModalVisible(true)} type='link'>Override Temperature</Button>
      </Row>
      <Row>
        {objects.length === 0
          ? 'This room does not have any object'
          : objects.map((item) => (
            <ObjectIcon
              addConsoleMessage={addConsoleMessage}
              roomName={name}
              object={item}
              fetchUserProfiles={fetchUserProfiles} />
            ))}
      </Row>
    </Card>
  )
}

RoomCard.displayName = 'RoomCard'
export default RoomCard
