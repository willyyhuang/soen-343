import React, {useState, useEffect} from 'react'
import {
Card, Row, Col, Typography,
} from 'antd'
import moment from 'moment'
import Clock from 'react-clock'
import {RoomCard} from '../index'
import 'react-clock/dist/Clock.css'
import {setSimulationDate, setSimulationTime} from '../../services'

const SimulationFunctionalityCard = ({
  addConsoleMessage,
  speedRate,
  simulationConfig,
  fetchUserProfiles,
}) => {
  const {
    simulationUsers,
    simulationRunning,
    homeLayout,
    date,
    time,
  } = simulationConfig
  const [currentTime, setCurrentTime] = useState(moment(date.concat(time)))
  const [secondsPassed, setSecondsPassed] = useState(0)
  const [value, setValue] = useState(
    new Date(currentTime.format('YYYY-MM-DD HH:mm:ss')),
  )

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentTime(currentTime.add(1, 'seconds'))
      setValue(new Date(currentTime.format('YYYY-MM-DD HH:mm:ss')))
      setSecondsPassed(secondsPassed + 1)
    }, 1000 / speedRate)
    return () => {
      clearInterval(interval)
      currentTime.add(secondsPassed * speedRate, 'seconds')
      const formattedDate = currentTime.format()
      setSimulationDate(
        formattedDate.substring(0, formattedDate.indexOf('T')),
      )
      setSimulationTime(
        formattedDate.substring(
          formattedDate.indexOf('T'),
          formattedDate.length,
        ),
      )
      fetchUserProfiles()
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
              {`Simulation start time: ${moment(date.concat(time)).format(
                'LLLL',
              )}`}
            </Typography.Text>
          </Row>
        </Col>
        <Col span={4}>
          <Clock value={value} />
        </Col>
      </Row>
      <Row gutter={[16, 16]}>
        {simulationRunning
          ? homeLayout
            && homeLayout.roomList.map((room) => {
              const {name} = room
              const users = simulationUsers.filter(
                (user) => user.homeLocation === name,
              )
              return (
                <Col span={8}>
                  <RoomCard
                    users={users}
                    key={name}
                    room={room}
                    addConsoleMessage={addConsoleMessage}
                    fetchUserProfiles={fetchUserProfiles} />
                </Col>
              )
            })
          : 'Simulation is off'}
      </Row>
    </Card>
  )
}

SimulationFunctionalityCard.displayName = 'SimulationFunctionalityCard'
export default SimulationFunctionalityCard
