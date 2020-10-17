import {
  Button, Card, Upload, InputNumber, Form, TimePicker, DatePicker,
} from 'antd'
import React, {useState} from 'react'
import moment from 'moment'
import {
  setSimulationDate, setSimulationTime, setSimulationInsideTemp, setSimulationOutsideTemp, uploadLayout,
} from '../../services'

const SimulationParameterCard = ({simulationConfig, fetchUserProfiles}) => {
  const {
    insideTemp, outsideTemp, time, date,
  } = simulationConfig
  const PARAMETER_FORM_DATA_INITIAL_STATE = {
    insideTemp,
    outsideTemp,
    time: time ? moment(time) : null,
    date: date ? moment(date) : null,
  }
  const [parameterFormData, setParameterFormData] = useState(PARAMETER_FORM_DATA_INITIAL_STATE)

  return (
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
        <Button>
          Upload House Layout File
        </Button>
      </Upload>}
      actions={[<Button onClick={() => {
        if (parameterFormData.insideTemp !== PARAMETER_FORM_DATA_INITIAL_STATE.insideTemp) {
          setSimulationInsideTemp(parameterFormData.insideTemp)
        }
        if (parameterFormData.outsideTemp !== PARAMETER_FORM_DATA_INITIAL_STATE.outsideTemp) {
          setSimulationOutsideTemp(parameterFormData.outsideTemp)
        }
        if (parameterFormData.time !== PARAMETER_FORM_DATA_INITIAL_STATE.time) {
          setSimulationTime(parameterFormData.time.format('LT'))
        }
        if (parameterFormData.date !== PARAMETER_FORM_DATA_INITIAL_STATE.date) {
          setSimulationDate(parameterFormData.date.format('L'))
        }
        fetchUserProfiles()
      }}>
        Save
      </Button>]}
      title='Set up simulation parameter'>
      <Form.Item label='Temperature Indoor (°C)'>
        <InputNumber
          onChange={(value) => {
            setParameterFormData({
              insideTemp: value,
              outsideTemp: parameterFormData.outsideTemp,
              time: parameterFormData.time,
              date: parameterFormData.date,
            })
          }}
          value={parameterFormData.insideTemp} />
      </Form.Item>
      <Form.Item label='Temperature Outdoor (°C)'>
        <InputNumber
          onChange={(value) => setParameterFormData({
            insideTemp: parameterFormData.insideTemp,
            outsideTemp: value,
            time: parameterFormData.time,
            date: parameterFormData.date,
          })}
          value={parameterFormData.outsideTemp} />
      </Form.Item>
      <Form.Item label='Date'>
        <DatePicker
          onChange={(value) => setParameterFormData({
            insideTemp: parameterFormData.insideTemp,
            outsideTemp: parameterFormData.outsideTemp,
            time: parameterFormData.time,
            date: value,
          })}
          placeholder='enter a date'
          value={parameterFormData.date} />
      </Form.Item>
      <Form.Item label='Time'>
        <TimePicker
          onChange={(value) => setParameterFormData({
            insideTemp: parameterFormData.insideTemp,
            outsideTemp: parameterFormData.outsideTemp,
            time: value,
            date: parameterFormData.date,
          })}
          placeholder='enter a time'
          value={parameterFormData.time} />
      </Form.Item>
    </Card>
  )
}

SimulationParameterCard.displayName = 'SimulationParameterCard'
export default SimulationParameterCard
