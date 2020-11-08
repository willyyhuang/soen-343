import {
Button, Card, Col, Divider, Layout, Row, Switch, Typography,
} from 'antd'
import React, {useEffect, useState} from 'react'
import {connect} from 'react-redux'
import {
  SimulationParameterCard,
  SimulationProfileCard,
  SimulationFunctionalityCard,
  OutputConsole,
  EditAwayModeModal,
} from '../index'
import './Dashboard.css'
import {
getProfile, start, stop, setAutoMode, setAwayMode,
} from '../../services'

const Dashboard = ({simulationConfig, consoleMessage, dispatch}) => {
  const {messages} = consoleMessage
  const [speedRate, setSpeedRate] = useState(1)
  const [editAwayModeModalVisible, setEditAwayModeModalVisible] = useState(false)

  const addConsoleMessage = (message) => {
    dispatch({type: 'ADD_CONSOLE_MESSAGE', payload: message})
  }
  const fetchUserProfiles = () => {
    getProfile().then((response) => {
      const {data} = response
      dispatch({type: 'SET_SIMULATION_CONFIG_STATE', payload: data})
    })
  }

  useEffect(() => {
    fetchUserProfiles()
    // eslint-disable-next-line
  }, [])

  const getAwayModeDisableState = () => {
    const {simulationUsers} = simulationConfig
    if (!simulationUsers || simulationUsers.length === 0) return false
    const userInHouse = simulationUsers.filter((user) => user.homeLocation !== 'outside')
    if (userInHouse.length > 0) return true
    return false
  }

  const simulationSwitchCard = (
    <Card>
      <Row>
        <Switch
          className='item'
          checked={simulationConfig.simulationRunning}
          onChange={(value) =>
            (value
              ? start() && fetchUserProfiles()
              : stop() && fetchUserProfiles())} />
        <Typography.Text>Simulation Mode</Typography.Text>
      </Row>
      <Row className='row'>
        <Switch
          className='item'
          checked={simulationConfig.autoMode}
          onChange={(value) => setAutoMode(value) && fetchUserProfiles()} />
        <Typography.Text>Light Auto Mode</Typography.Text>
      </Row>
      {simulationConfig.homeLayout && <Row className='row'>
        <Col>
          <Switch
            disabled={getAwayModeDisableState()}
            className='item'
            checked={simulationConfig.awayMode}
            onChange={(value) => setAwayMode(value).then((response) => {
              const {data} = response
              addConsoleMessage(data.consoleMessage)
            }) && fetchUserProfiles()} />
        </Col>
        <Col>
          <Typography.Text>Away Mode (SHP)</Typography.Text>
        </Col>
        {simulationConfig.awayMode
          && <Col>
            <Button className='button' onClick={() => setEditAwayModeModalVisible(true)}>Edit Parameters</Button>
          </Col>}
      </Row>}
    </Card>
  )

  return (
    <Layout className='layout'>
      <Layout.Content className='content'>
        <Row type='flex' align='top'>
          {editAwayModeModalVisible && simulationConfig.homeLayout
            && <EditAwayModeModal
              visible={editAwayModeModalVisible}
              simulationConfig={simulationConfig}
              onClose={() => setEditAwayModeModalVisible(false)}
              fetchUserProfiles={fetchUserProfiles} />}
          <Col span={1} />
          <Col span={6}>
            <SimulationParameterCard
              speedRate={speedRate}
              setSpeedRate={setSpeedRate}
              simulationConfig={simulationConfig}
              fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            {simulationConfig.homeLayout && (
              <>
                <SimulationProfileCard
                  addConsoleMessage={addConsoleMessage}
                  simulationConfig={simulationConfig}
                  fetchUserProfiles={fetchUserProfiles} />
                <Divider />
              </>
            )}
            {simulationSwitchCard}
          </Col>
          <Col span={1} />
          <Col span={15}>
            {simulationConfig.simulationRunning
              && simulationConfig.date
              && simulationConfig.time && (
                <SimulationFunctionalityCard
                  addConsoleMessage={addConsoleMessage}
                  speedRate={speedRate}
                  simulationConfig={simulationConfig}
                  fetchUserProfiles={fetchUserProfiles} />
              )}
          </Col>
        </Row>
      </Layout.Content>
      <Layout.Footer style={{padding: 0}}>
        <OutputConsole messages={messages} />
      </Layout.Footer>
    </Layout>
  )
}

const mapStateToProps = (state) => ({
  simulationConfig: state.simulationConfig,
  consoleMessage: state.consoleMessage,
})

Dashboard.displayName = 'Dashboard'
export default connect(mapStateToProps)(Dashboard)
