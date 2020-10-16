import {
  Button, Card, Upload, InputNumber, Form, TimePicker, DatePicker,
} from 'antd'
import React from 'react'

const SimulationParameterCard = () => (
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

SimulationParameterCard.displayName = 'SimulationParameterCard'
export default SimulationParameterCard
