import {
Button, Card, Form, Input, InputNumber, Modal, Select,
} from 'antd'
import React, {useState} from 'react'

const SmartHomeHeatingCard = ({
    simulationConfig,
}) => {
    const {homeLayout} = simulationConfig
    const {roomList} = homeLayout
    const [isSetZoneModalVisible, setIsSetZoneModalVisible] = useState(false)
    const setZoneModal = (
      <Modal title='Add Zone' visible={isSetZoneModalVisible} onCancel={() => setIsSetZoneModalVisible(false)}>
        <Form.Item label='Zone Name'>
          <Input placeholder='Enter a zone name' />
        </Form.Item>
        <Form.Item label='Period 1'>
          <Input placeholder='e.g: 09:00' />
        </Form.Item>
        <Form.Item label='Period 1 Temperature'>
          <InputNumber />
        </Form.Item>
        <Form.Item label='Period 2'>
          <Input placeholder='e.g: 14:00' />
        </Form.Item>
        <Form.Item label='Period 2 Temperature'>
          <InputNumber />
        </Form.Item>
        <Form.Item label='Period 3'>
          <Input placeholder='e.g: 18:00' />
        </Form.Item>
        <Form.Item label='Period 3 Temperature'>
          <InputNumber />
        </Form.Item>
        <Form.Item label='Rooms in Zone'>
          <Select mode='multiple'>
            {roomList.length === 0
            ? null
            : roomList.map((room) => (
              <Select.Option value={room.name}>{room.name}</Select.Option>
              ))}
          </Select>
        </Form.Item>
      </Modal>
    )
    return <Card title='Smart Home Heating'>
      {setZoneModal}
      <Button onClick={() => setIsSetZoneModalVisible(true)}>Add Zone</Button>
    </Card>
}

SmartHomeHeatingCard.displayName = 'SmartHomeHeatingCard'
export default SmartHomeHeatingCard
