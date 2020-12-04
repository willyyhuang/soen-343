import {
Button, Card, Form, Input, InputNumber, Modal, Select, Typography,
} from 'antd'
import React, {useState} from 'react'
import {FireOutlined} from '@ant-design/icons'
import {createZone} from '../../services'

const SmartHomeHeatingCard = ({
    simulationConfig,
}) => {
    const {homeLayout} = simulationConfig
    const {roomList} = homeLayout
    const [isSetZoneModalVisible, setIsSetZoneModalVisible] = useState(false)
    const [name, setName] = useState()
    const [desiredTemp, setDesiredTemp] = useState()
    const [period1, setPeriod1] = useState()
    const [period1Temp, setPeriod1Temp] = useState()
    const [period2, setPeriod2] = useState()
    const [period2Temp, setPeriod2Temp] = useState()
    const [period3, setPeriod3] = useState()
    const [period3Temp, setPeriod3Temp] = useState()
    const [roomsInZone, setRoomsInZone] = useState()

    const setZoneModal = (
      <Modal
        title='Add Zone'
        visible={isSetZoneModalVisible}
        onCancel={() => setIsSetZoneModalVisible(false)}
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
          createZone(createZonePayload)
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
      {setZoneModal}
      <Button onClick={() => setIsSetZoneModalVisible(true)}>Add Zone</Button>
    </Card>
}

SmartHomeHeatingCard.displayName = 'SmartHomeHeatingCard'
export default SmartHomeHeatingCard
