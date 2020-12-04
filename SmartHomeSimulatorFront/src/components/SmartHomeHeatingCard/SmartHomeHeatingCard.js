import {
Button, Card, Form, Input, InputNumber, Modal, Row, Select, Tooltip, Typography,
} from 'antd'
import React, {useEffect, useState} from 'react'
import {FireOutlined} from '@ant-design/icons'
import {createZone, fetchZone} from '../../services'

const SmartHomeHeatingCard = ({
    simulationConfig,
    addConsoleMessage,
    zone,
    setZone,
}) => {
    const {homeLayout} = simulationConfig
    const {roomList} = homeLayout
    const [isSetZoneModalVisible, setIsSetZoneModalVisible] = useState(false)
    const [name, setName] = useState(null)
    const [desiredTemp, setDesiredTemp] = useState(null)
    const [period1, setPeriod1] = useState(null)
    const [period1Temp, setPeriod1Temp] = useState(null)
    const [period2, setPeriod2] = useState(null)
    const [period2Temp, setPeriod2Temp] = useState(null)
    const [period3, setPeriod3] = useState(null)
    const [period3Temp, setPeriod3Temp] = useState(null)
    const [roomsInZone, setRoomsInZone] = useState(null)

    const fetchZoneData = () => {
      fetchZone().then((zoneResponse) => {
        setZone(zoneResponse.data)
      })
    }

    useEffect(() => {
      fetchZoneData()
      // eslint-disable-next-line
    }, [])

    const resetFormData = () => {
      setName(null)
      setDesiredTemp(null)
      setPeriod1(null)
      setPeriod2(null)
      setPeriod3(null)
      setPeriod1Temp(null)
      setPeriod2Temp(null)
      setPeriod3Temp(null)
      setRoomsInZone([])
    }

    const setZoneModal = (
      <Modal
        title='Add Zone'
        visible={isSetZoneModalVisible}
        onCancel={() => {
          setIsSetZoneModalVisible(false)
          resetFormData()
        }}
        onOk={() => {
          const createZonePayload = {
            name,
            desiredTemp,
            period1,
            period1Temp,
            period2,
            period2Temp,
            period3,
            period3Temp,
            roomsInZone,
          }
          createZone(createZonePayload).then((response) => {
            const {data} = response
            if (data.success) {
              fetchZoneData()
              addConsoleMessage(data.consoleMessage)
            }
          })
          resetFormData()
          setIsSetZoneModalVisible(false)
        }}>
        <Form.Item label='Zone Name'>
          <Input placeholder='Enter a zone name' onChange={(e) => setName(e.target.value)} />
        </Form.Item>
        <Form.Item label='Desired Room Temperature'>
          <InputNumber onChange={(value) => setDesiredTemp(value)} />
        </Form.Item>
        <Form.Item label='Period 1'>
          <Input placeholder='e.g: 09:00' onChange={(e) => setPeriod1(e.target.value)} />
        </Form.Item>
        <Form.Item label='Period 1 Temperature'>
          <InputNumber onChange={(value) => setPeriod1Temp(value)} />
        </Form.Item>
        <Form.Item label='Period 2'>
          <Input placeholder='e.g: 14:00' onChange={(e) => setPeriod2(e.target.value)} />
        </Form.Item>
        <Form.Item label='Period 2 Temperature'>
          <InputNumber onChange={(value) => setPeriod2Temp(value)} />
        </Form.Item>
        <Form.Item label='Period 3'>
          <Input placeholder='e.g: 18:00' onChange={(e) => setPeriod3(e.target.value)} />
        </Form.Item>
        <Form.Item label='Period 3 Temperature'>
          <InputNumber onChange={(value) => setPeriod3Temp(value)} />
        </Form.Item>
        <Form.Item label='Rooms in Zone'>
          <Select onChange={(value) => setRoomsInZone(value)} mode='multiple'>
            {roomList.length === 0
            ? null
            : roomList.map((room) => (
              <Select.Option value={room.name}>{room.name}</Select.Option>
              ))}
          </Select>
        </Form.Item>
      </Modal>
    )
    return <Card
      title={<Typography.Text>
        <FireOutlined style={{marginRight: 10}} />
        Smart Home Heating
      </Typography.Text>}>
      <Button onClick={() => setIsSetZoneModalVisible(true)}>Add Zone</Button>
      {setZoneModal}
      <Row style={{marginTop: 5}}>
        {zone.length > 0 && zone.map((info) => (
          <Tooltip title={info.rooms} placement='bottom' trigger='hover'>
            <Button>
              {info.zoneName}
            </Button>
          </Tooltip>
        ))}
      </Row>
    </Card>
}

SmartHomeHeatingCard.displayName = 'SmartHomeHeatingCard'
export default SmartHomeHeatingCard
