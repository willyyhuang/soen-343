import {
  Card, Col, Divider, Layout, Row, Switch, Typography,
} from 'antd'
import React, {useEffect, useState} from 'react'
import {connect} from 'react-redux'
import {SimulationParameterCard, SimulationProfileCard, SimulationFunctionalityCard} from '../index'
import './Dashboard.css'
import {
  getProfile, start, stop, setAutoMode, setAwayMode,
} from '../../services'

const Dashboard = ({simulationConfig, consoleMessage, dispatch}) => {
  const {messages} = consoleMessage
  const [speedRate, setSpeedRate] = useState(1)
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

  const simulationSwitchCard = (
    <Card>
      <Row>
        <Switch
          className='item'
          checked={simulationConfig.simulationRunning}
          onChange={(value) => (value ? start() && fetchUserProfiles() : stop() && fetchUserProfiles())} />
        <Typography.Text>Simulation Mode</Typography.Text>
      </Row>
      <Row style={{marginTop: 5}}>
        <Switch
          className='item'
          checked={simulationConfig.autoMode}
          onChange={(value) => setAutoMode(value) && fetchUserProfiles()} />
        <Typography.Text>Light Auto Mode</Typography.Text>
      </Row>
      <Row style={{marginTop: 5}}>
        <Switch
          className='item'
          onChange={(value) => setAwayMode(value) && fetchUserProfiles()} />
        <Typography.Text>Away Mode</Typography.Text>
      </Row>
    </Card>
  )

  return (
    <Layout className='layout'>
      <Layout.Content className='content'>
        <Row type='flex' align='top'>
          <Col span={1} />
          <Col span={6}>
            <SimulationParameterCard speedRate={speedRate} setSpeedRate={setSpeedRate} simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            <SimulationProfileCard simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            {simulationSwitchCard}
          </Col>
          <Col span={1} />
          <Col span={15}>
            {simulationConfig.simulationRunning && simulationConfig.date && simulationConfig.time
              && <SimulationFunctionalityCard speedRate={speedRate} simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />}
          </Col>
        </Row>
      </Layout.Content>
      <Layout.Footer style={{padding: 0}}>
        <Card title='Output Console'>
          {messages.length > 0 ? messages.forEach((message) => (
            <Row>
              <Typography.Text>{message}</Typography.Text>
            </Row>
          )) : null}
        </Card>
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
