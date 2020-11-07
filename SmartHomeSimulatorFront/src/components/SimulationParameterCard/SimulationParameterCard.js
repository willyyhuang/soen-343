import {
  Button, Card, Upload, InputNumber, Form, TimePicker, DatePicker,
} from 'antd'
import React, {useEffect, useState} from 'react'
import moment from 'moment'
import {
  setSimulationDate, setSimulationTime, setSimulationInsideTemp, setSimulationOutsideTemp, uploadLayout, stop,
} from '../../services'

const SimulationParameterCard = ({
  simulationConfig, fetchUserProfiles, setSpeedRate, speedRate,
}) => {
  const {
    insideTemp, outsideTemp, time, date, simulationRunning,
  } = simulationConfig
  const PARAMETER_FORM_DATA_INITIAL_STATE = {
    insideTemp,
    outsideTemp,
    time: time ? moment(date.concat(time)) : null,
    date: date ? moment(date) : null,
  }
  const [parameterFormData, setParameterFormData] = useState(PARAMETER_FORM_DATA_INITIAL_STATE)
  const [speedRateFormData, setSpeedRateFormData] = useState(speedRate)

  useEffect(() => {
    setParameterFormData({
      insideTemp,
      outsideTemp,
      time: time ? moment(date.concat(time)) : null,
      date: date ? moment(date) : null,
    })
    // eslint-disable-next-line
  }, [simulationConfig])

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
          const formattedTime = parameterFormData.time.format()
          setSimulationTime(formattedTime.substring(formattedTime.indexOf('T'), formattedTime.length))
        }
        if (parameterFormData.date !== PARAMETER_FORM_DATA_INITIAL_STATE.date) {
          const formattedDate = parameterFormData.date.format()
          setSimulationDate(formattedDate.substring(0, formattedDate.indexOf('T')))
        }
        if (speedRateFormData !== speedRate) {
          setSpeedRate(speedRateFormData)
        }
        fetchUserProfiles()
        if (simulationRunning) {
          stop()
        }
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
          onChange={(value) => {
            setParameterFormData({
              insideTemp: parameterFormData.insideTemp,
              outsideTemp: parameterFormData.outsideTemp,
              time: value,
              date: parameterFormData.date,
            })
          }}
          placeholder='enter a time'
          value={parameterFormData.time} />
      </Form.Item>
      <Form.Item label='Speed Rate'>
        <InputNumber
          step={0.1}
          min={0}
          max={5}
          onChange={(value) => setSpeedRateFormData(value)}
          value={speedRateFormData} />
      </Form.Item>
    </Card>
  )
}

SimulationParameterCard.displayName = 'SimulationParameterCard'
export default SimulationParameterCard
