import {
  Button, Card, Upload, InputNumber, Form, TimePicker, DatePicker,
} from 'antd'
import React from 'react'
import {uploadLayout} from '../../services'

const SimulationParameterCard = ({fetchUserProfiles}) => (
  <Card
    extra={<Upload
      customRequest={async (options) => {
        const {file, onSuccess} = options
        const reader = new FileReader()
        let payload
        reader.onloadend = (e) => {
          payload = e.target.result
          if (payload) {
            uploadLayout(payload).then((response) => {
              onSuccess(response.body)
              fetchUserProfiles()
            })
          }
        }
        reader.readAsText(file)
      }}>
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
