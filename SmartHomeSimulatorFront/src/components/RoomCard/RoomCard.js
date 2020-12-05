import {UserOutlined} from '@ant-design/icons'
import {
Button, Card, Form, InputNumber, Popover, Modal, Row, Tag, Typography,
} from 'antd'
import _ from 'lodash'
import React, {useEffect, useState} from 'react'
import {ObjectIcon} from '../index'
import {overrideRoomTemp, setCurrentTemp} from '../../services'

const RoomCard = ({
  addConsoleMessage,
  users,
  room,
  fetchUserProfiles,
  speedRate,
  outsideTemp,
}) => {
  const {
    desiredTemp, currentTemp, zone, name, objects, overridden,
  } = room
  const [currentTemperature, setCurrentTemperature] = useState(currentTemp)
  const [isCurrentTempDisplayed, setIsCurrentTempDisplayed] = useState(false)
  const [isOverrideModalVisible, setIsOverrideModalVisible] = useState(false)
  const [newRoomTemp, setNewRoomTemp] = useState()
  const heaterObject = objects.filter((item) => item.objectType === 'HEATER')
  const acObject = objects.filter((item) => item.objectType === 'AC')
  // Temperature
  useEffect(() => {
    const interval = setInterval(() => {
      if (acObject.length === 1 && heaterObject.length === 1) {
        if (heaterObject[0].status === false && acObject[0].status === false) {
          if (_.round(currentTemperature, 1) > outsideTemp) {
            setCurrentTemperature(_.round(currentTemperature, 2) - 0.05)
          }
          if (_.round(currentTemperature, 1) < outsideTemp) {
            setCurrentTemperature(_.round(currentTemperature, 2) + 0.05)
          }
        }
        if (acObject[0].status && _.round(currentTemperature, 2) > desiredTemp) {
          setCurrentTemperature(_.round(currentTemperature, 2) - 0.1)
        }
        if (heaterObject[0].status && _.round(currentTemperature, 1) < desiredTemp) {
          setCurrentTemperature(_.round(currentTemperature, 2) + 0.1)
        }
        if (_.round(currentTemperature, 2) === desiredTemp && (heaterObject[0].status || acObject[0].status)) {
          setCurrentTemp({
            roomName: name,
            currentTemp: _.round(currentTemperature, 2),
          })
          fetchUserProfiles()
        }
      }
    }, 1000 / speedRate)
    return () => {
      clearInterval(interval)
    }
  }, [currentTemperature])

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
        <InputNumber precision={1} step={0.1} onChange={(value) => setNewRoomTemp(value)} value={newRoomTemp} />
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
            {`Current Temperature: ${_.round(currentTemperature, 2)}`}
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
