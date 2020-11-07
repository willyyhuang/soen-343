import React, {useState, useEffect} from 'react'
import {
  Card, Row, Col, Typography,
} from 'antd'
import moment from 'moment'
import Clock from 'react-clock'
import {RoomCard} from '../index'
import 'react-clock/dist/Clock.css'

const SimulationFunctionalityCard = ({speedRate, simulationConfig, fetchUserProfiles}) => {
  const {
    simulationRunning, homeLayout, date, time,
  } = simulationConfig
  const [currentTime, setCurrentTime] = useState(moment(date.concat(time)))
  const [value, setValue] = useState(new Date(currentTime.format('YYYY-MM-DD HH:mm:ss')))

  useEffect(() => {
    const interval = setInterval(
      () => {
        setCurrentTime(currentTime.add(1, 'seconds'))
        setValue(new Date(currentTime.format('YYYY-MM-DD HH:mm:ss')))
      },
      1000 / speedRate,
    )
    return () => {
      clearInterval(interval)
    }
    // eslint-disable-next-line
  }, [simulationConfig])

  return (
    <Card title='Smart Home Core Functionality'>
      <Row>
        <Col span={20}>
          <Row>
            <Typography.Text>Current time:</Typography.Text>
          </Row>
          <Row>
            <Typography.Text>
              {`Simulation start time: ${moment(date.concat(time)).format('LLLL')}`}
            </Typography.Text>
          </Row>
        </Col>
        <Col span={4}>
          <Clock value={value} />
        </Col>
      </Row>
      <Row gutter={[16, 16]}>
        {simulationRunning
          ? homeLayout && homeLayout.roomList.map((room) => <Col span={8}>
            <RoomCard key={room.name} room={room} fetchUserProfiles={fetchUserProfiles} />
          </Col>)
          : 'Simulation is off'}
      </Row>
    </Card>
  )
}

SimulationFunctionalityCard.displayName = 'SimulationFunctionalityCard'
export default SimulationFunctionalityCard
