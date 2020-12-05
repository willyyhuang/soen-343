import React, {useState, useEffect} from 'react'
import {
Card, Row, Col, Typography,
} from 'antd'
import moment from 'moment'
import Clock from 'react-clock'
import {RoomCard} from '../index'
import 'react-clock/dist/Clock.css'
import {setSimulationDate, setSimulationTime, setAwayModeLightStatus} from '../../services'

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
    awayMode,
    startLightsOn,
    endLightsOn,
    outsideTemp,
  } = simulationConfig
  const [autoLightOn, setAutoLightOn] = useState(false)
  const [currentTime, setCurrentTime] = useState(moment(date.concat(time)))
  const [secondsPassed, setSecondsPassed] = useState(0)
  const [value, setValue] = useState(
    new Date(currentTime.format('YYYY-MM-DD HH:mm:ss')),
  )
  const startLightsOnMomentObj = moment(date.concat(`T${startLightsOn}`))
  const endLightsOnMomentObj = moment(date.concat(`T${endLightsOn}`))

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentTime(currentTime.add(1, 'seconds'))
      setValue(new Date(currentTime.format('YYYY-MM-DD HH:mm:ss')))
      setSecondsPassed(secondsPassed + 1)
      if (awayMode) {
        if (currentTime.isBetween(startLightsOnMomentObj, endLightsOnMomentObj)) {
          if (!autoLightOn) {
            setAwayModeLightStatus(true).then((response) => {
              const {data} = response
              if (data) {
                addConsoleMessage(data.consoleMessage)
                setTimeout(() => {
                  setAutoLightOn(true)
                  fetchUserProfiles()
                }, 2000)
              }
            })
          }
        }
        if (autoLightOn && !currentTime.isBetween(startLightsOnMomentObj, endLightsOnMomentObj)) {
          setAwayModeLightStatus(false).then((response) => {
            const {data} = response
            if (data) {
              addConsoleMessage(data.consoleMessage)
              setTimeout(() => {
                setAutoLightOn(false)
                fetchUserProfiles()
              }, 2000)
            }
          })
        }
      }
    }, 1000 / speedRate)
    return () => {
      clearInterval(interval)
    }
    // eslint-disable-next-line
  }, [simulationConfig])

  useEffect(() => () => {
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
      // eslint-disable-next-line
    }, [])
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
                    outsideTemp={outsideTemp}
                    speedRate={speedRate}
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
