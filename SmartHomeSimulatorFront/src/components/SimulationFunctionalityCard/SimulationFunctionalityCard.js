import React from 'react'
import {
  Card, Row, Col, Typography,
} from 'antd'
import moment from 'moment'
import {RoomCard} from '../index'

const SimulationFunctionalityCard = ({simulationConfig, fetchUserProfiles}) => {
  const {
    simulationRunning, homeLayout, date, time,
  } = simulationConfig

  return (
    <Card title='Smart Home Core Functionality'>
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
