import {
  Button,
  Card,
  Divider,
  Upload,
  InputNumber,
  Form,
  TimePicker,
  DatePicker,
  Typography,
  Input,
} from 'antd'
import React, {useEffect, useState} from 'react'
import moment from 'moment'
import {SettingOutlined, HomeOutlined} from '@ant-design/icons'
import {
  setSimulationDate,
  setSimulationTime,
  setSimulationOutsideTemp,
  uploadLayout,
  stop,
  setSummerMonths,
  setSummerTemp,
  setWinterMonths,
  setWinterTemp,
  setSeason,
  setEmptyRoomTemp,
  setTempThreshold,
} from '../../services'

const SimulationParameterCard = ({
  simulationConfig,
  fetchUserProfiles,
  setSpeedRate,
  speedRate,
}) => {
  const {
    outsideTemp,
    time,
    date,
    simulationRunning,
    summerMonths,
    winterMonths,
    summerTemp,
    winterTemp,
    emptyRoomTemp,
    tempThreshold,
  } = simulationConfig
  const PARAMETER_FORM_DATA_INITIAL_STATE = {
    outsideTemp,
    time: time ? moment(date.concat(time)) : null,
    date: date ? moment(date) : null,
    summerMonths,
    winterMonths,
    summerTemp,
    winterTemp,
    emptyRoomTemp,
    tempThreshold,
  }
  const [parameterFormData, setParameterFormData] = useState(
    PARAMETER_FORM_DATA_INITIAL_STATE,
  )
  const [speedRateFormData, setSpeedRateFormData] = useState(speedRate)

  useEffect(() => {
    setParameterFormData({
      outsideTemp,
      time: time ? moment(date.concat(time)) : null,
      date: date ? moment(date) : null,
      summerMonths,
      winterMonths,
      summerTemp,
      winterTemp,
      emptyRoomTemp,
      tempThreshold,
    })
    // eslint-disable-next-line
  }, [simulationConfig])

  useEffect(() => {
    if (summerMonths && date && time) {
      const startMonth = parseInt(summerMonths.substring(0, summerMonths.indexOf('-')), 10) - 1
      const endMonth = parseInt(summerMonths.substring(summerMonths.indexOf('-') + 1, summerMonths.length), 10) - 1
      if (moment(date.concat(time)).get('month') >= startMonth && moment(date.concat(time)).get('month') < endMonth) {
        setSeason(true)
      }
    }
    if (winterMonths && date && time) {
      const startMonth = winterMonths.substring(0, winterMonths.indexOf('-')) - 1
      let endMonth = winterMonths.substring(winterMonths.indexOf('-') + 1, winterMonths.length) - 1
      if (startMonth > endMonth) {
        endMonth += 11
      }
      if (moment(date.concat(time)).get('month') >= startMonth && moment(date.concat(time)).get('month') < endMonth) {
        setSeason(false)
      }
    }
  }, [date, time, winterMonths, summerMonths])

  return (
    <Card
      extra={
        <Upload
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
            <HomeOutlined />
            Upload House Layout File
          </Button>
        </Upload>
      }
      actions={[
        <Button
          onClick={() => {
            if (
              parameterFormData.outsideTemp
              !== PARAMETER_FORM_DATA_INITIAL_STATE.outsideTemp
            ) {
              setSimulationOutsideTemp(parameterFormData.outsideTemp)
            }
            if (
              parameterFormData.time !== PARAMETER_FORM_DATA_INITIAL_STATE.time
            ) {
              const formattedTime = parameterFormData.time.format()
              setSimulationTime(
                formattedTime.substring(
                  formattedTime.indexOf('T'),
                  formattedTime.length,
                ),
              )
            }
            if (
              parameterFormData.date !== PARAMETER_FORM_DATA_INITIAL_STATE.date
            ) {
              const formattedDate = parameterFormData.date.format()
              setSimulationDate(
                formattedDate.substring(0, formattedDate.indexOf('T')),
              )
            }
            if (speedRateFormData !== speedRate) {
              setSpeedRate(speedRateFormData)
            }
            if (
              parameterFormData.summerMonths
              !== PARAMETER_FORM_DATA_INITIAL_STATE.summerMonths
            ) {
              setSummerMonths(parameterFormData.summerMonths)
            }
            if (
              parameterFormData.winterMonths
              !== PARAMETER_FORM_DATA_INITIAL_STATE.winterMonths
            ) {
              setWinterMonths(parameterFormData.winterMonths)
            }
            if (
              parameterFormData.summerTemp
              !== PARAMETER_FORM_DATA_INITIAL_STATE.summerTemp
            ) {
              setSummerTemp(parameterFormData.summerTemp)
            }
            if (
              parameterFormData.winterTemp
              !== PARAMETER_FORM_DATA_INITIAL_STATE.winterTemp
            ) {
              setWinterTemp(parameterFormData.winterTemp)
            }
            if (parameterFormData.emptyRoomTemp !== PARAMETER_FORM_DATA_INITIAL_STATE.emptyRoomTemp) {
              setEmptyRoomTemp(parameterFormData.emptyRoomTemp)
            }
            if (parameterFormData.tempThreshold !== PARAMETER_FORM_DATA_INITIAL_STATE.tempThreshold) {
              setTempThreshold(parameterFormData.tempThreshold)
            }
            fetchUserProfiles()
            if (simulationRunning) {
              stop()
            }
          }}>
          Save
        </Button>,
      ]}
      title={
        <Typography.Text>
          <SettingOutlined style={{marginRight: 10}} />
          Simulation Parameter
        </Typography.Text>
      }>
      <Form.Item label='Temperature Outdoor (°C)'>
        <InputNumber
          step={0.1}
          precision={1}
          onChange={(value) =>
            setParameterFormData({
              summerMonths: parameterFormData.summerMonths,
              summerTemp: parameterFormData.summerTemp,
              winterMonths: parameterFormData.winterMonths,
              winterTemp: parameterFormData.winterTemp,
              outsideTemp: value,
              time: parameterFormData.time,
              date: parameterFormData.date,
              emptyRoomTemp: parameterFormData.emptyRoomTemp,
              tempThreshold: parameterFormData.tempThreshold,
            })}
          value={parameterFormData.outsideTemp} />
      </Form.Item>
      <Form.Item label='Date'>
        <DatePicker
          onChange={(value) =>
            setParameterFormData({
              summerMonths: parameterFormData.summerMonths,
              summerTemp: parameterFormData.summerTemp,
              winterMonths: parameterFormData.winterMonths,
              winterTemp: parameterFormData.winterTemp,
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
              summerMonths: parameterFormData.summerMonths,
              summerTemp: parameterFormData.summerTemp,
              winterMonths: parameterFormData.winterMonths,
              winterTemp: parameterFormData.winterTemp,
              outsideTemp: parameterFormData.outsideTemp,
              time: value,
              date: parameterFormData.date,
              emptyRoomTemp: parameterFormData.emptyRoomTemp,
              tempThreshold: parameterFormData.tempThreshold,
            })
          }}
          placeholder='enter a time'
          value={parameterFormData.time} />
      </Form.Item>
      <Divider />
      <Typography.Title level={5}>SHH Parameter</Typography.Title>
      <Form.Item label='Alert Threshold Temperature (°C)'>
        <InputNumber
          step={0.1}
          precision={1}
          value={parameterFormData.tempThreshold}
          onChange={(value) => setParameterFormData({
          summerMonths: parameterFormData.summerMonths,
          summerTemp: parameterFormData.summerTemp,
          winterMonths: parameterFormData.winterMonths,
          winterTemp: parameterFormData.winterTemp,
          outsideTemp: parameterFormData.outsideTemp,
          time: parameterFormData.time,
          date: parameterFormData.date,
          emptyRoomTemp: parameterFormData.emptyRoomTemp,
          tempThreshold: value,
        })} />
      </Form.Item>
      <Form.Item label='Desired Empty Room Temperature (°C)'>
        <InputNumber
          step={0.1}
          precision={1}
          value={parameterFormData.emptyRoomTemp}
          onChange={(value) => setParameterFormData({
          summerMonths: parameterFormData.summerMonths,
          summerTemp: parameterFormData.summerTemp,
          winterMonths: parameterFormData.winterMonths,
          winterTemp: parameterFormData.winterTemp,
          outsideTemp: parameterFormData.outsideTemp,
          time: parameterFormData.time,
          date: parameterFormData.date,
          emptyRoomTemp: value,
          tempThreshold: parameterFormData.tempThreshold,
        })} />
      </Form.Item>
      <Form.Item label='Summer Months'>
        <Input
          placeholder='e.g. 6-8'
          value={parameterFormData.summerMonths}
          onChange={(e) =>
            setParameterFormData({
          summerMonths: e.target.value,
          summerTemp: parameterFormData.summerTemp,
          winterMonths: parameterFormData.winterMonths,
          winterTemp: parameterFormData.winterTemp,
          outsideTemp: parameterFormData.outsideTemp,
          time: parameterFormData.time,
          date: parameterFormData.date,
          emptyRoomTemp: parameterFormData.emptyRoomTemp,
          tempThreshold: parameterFormData.tempThreshold,
        })} />
      </Form.Item>
      <Form.Item label='Winter Months'>
        <Input
          placeholder='e.g. 11-3'
          value={parameterFormData.winterMonths}
          onChange={(e) => setParameterFormData({
          summerMonths: parameterFormData.summerMonths,
          summerTemp: parameterFormData.summerTemp,
          winterMonths: e.target.value,
          winterTemp: parameterFormData.winterTemp,
          outsideTemp: parameterFormData.outsideTemp,
          time: parameterFormData.time,
          date: parameterFormData.date,
          emptyRoomTemp: parameterFormData.emptyRoomTemp,
          tempThreshold: parameterFormData.tempThreshold,
        })} />
      </Form.Item>
      <Form.Item label='Summer Temperature (°C)'>
        <InputNumber
          step={0.1}
          precision={1}
          value={parameterFormData.summerTemp}
          onChange={(value) => setParameterFormData({
          summerMonths: parameterFormData.summerMonths,
          summerTemp: value,
          winterMonths: parameterFormData.winterMonths,
          winterTemp: parameterFormData.winterTemp,
          outsideTemp: parameterFormData.outsideTemp,
          time: parameterFormData.time,
          date: parameterFormData.date,
          emptyRoomTemp: parameterFormData.emptyRoomTemp,
          tempThreshold: parameterFormData.tempThreshold,
        })} />
      </Form.Item>
      <Form.Item label='Winter Temperature (°C)'>
        <InputNumber
          step={0.1}
          precision={1}
          value={parameterFormData.winterTemp}
          onChange={(value) => setParameterFormData({
          summerMonths: parameterFormData.summerMonths,
          summerTemp: parameterFormData.summerTemp,
          winterMonths: parameterFormData.winterMonths,
          winterTemp: value,
          outsideTemp: parameterFormData.outsideTemp,
          time: parameterFormData.time,
          date: parameterFormData.date,
          emptyRoomTemp: parameterFormData.emptyRoomTemp,
          tempThreshold: parameterFormData.tempThreshold,
        })} />
      </Form.Item>
      <Divider />
      <Form.Item label='Speed Rate'>
        <InputNumber
          step={0.1}
          precision={1}
          min={0}
          max={5}
          onChange={(value) => setSpeedRateFormData(value)}
          value={speedRateFormData} />
      </Form.Item>
    </Card>
  )
}

export default SimulationParameterCard
