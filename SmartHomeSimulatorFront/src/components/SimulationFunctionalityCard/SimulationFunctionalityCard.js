import React, {useState, useEffect} from 'react'
import {
  Card, Row, Col, Typography,
} from 'antd'
import moment from 'moment'
import Clock from 'react-clock'
import {RoomCard} from '../index'
import 'react-clock/dist/Clock.css'

const SimulationFunctionalityCard = ({simulationConfig, fetchUserProfiles}) => {
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
      1000,
    )
    return () => {
      clearInterval(interval)
    }
  }, [simulationConfig])

  return (
    <Card title='Smart Home Core Functionality'>
      <div>
        <p>Current time:</p>
        <Clock value={value} />
      </div>
      {simulationRunning && <Row>
        <Typography.Text>
          {`Simulation start time: ${moment(date.concat(time)).format('LLLL')}`}
        </Typography.Text>
      </Row>}
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
