import React, {useState, useEffect} from 'react'
import {
Card, Row, Col, Typography,
} from 'antd'
import moment from 'moment'
import Clock from 'react-clock'
import {RoomCard} from '../index'
import 'react-clock/dist/Clock.css'
import {
setSimulationDate, setSimulationTime, setAwayModeLightStatus, setZonePeriod,
} from '../../services'

const SimulationFunctionalityCard = ({
  addConsoleMessage,
  speedRate,
  simulationConfig,
  fetchUserProfiles,
  zone,
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
      if (zone.length > 0) {
        // eslint-disable-next-line
        zone.map((item) => {
          const {
            period1, period2, period3, zoneName,
          } = item
          if (period1) {
            const hour = period1.substring(0, period1.indexOf(':'))
            const minute = period1.substring(period1.indexOf(':') + 1, period1.length)
            const compareTime = moment(date.concat(time)).set('hour', hour).set('minute', minute).set('second', 0)
            if (compareTime.isSame(currentTime)) {
              setZonePeriod({
                period: 1,
                zoneName,
              }).then((response) => {
                const {data} = response
                if (data.success) {
                  addConsoleMessage(data.consoleMessage)
                }
              })
            }
          }
          if (period2) {
            const hour = period2.substring(0, period2.indexOf(':'))
            const minute = period2.substring(period2.indexOf(':') + 1, period2.length)
            const compareTime = moment(date.concat(time)).set('hour', hour).set('minute', minute).set('second', 0)
            if (compareTime.isSame(currentTime)) {
              setZonePeriod({
                period: 2,
                zoneName,
              }).then((response) => {
                const {data} = response
                if (data.success) {
                  addConsoleMessage(data.consoleMessage)
                }
              })
            }
          }
          if (period3) {
            const hour = period3.substring(0, period3.indexOf(':'))
            const minute = period3.substring(period3.indexOf(':') + 1, period3.length)
            const compareTime = moment(date.concat(time)).set('hour', hour).set('minute', minute).set('second', 0)
            if (compareTime.isSame(currentTime)) {
              setZonePeriod({
                period: 3,
                zoneName,
              }).then((response) => {
                const {data} = response
                if (data.success) {
                  addConsoleMessage(data.consoleMessage)
                }
              })
            }
          }
        })
      }
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
