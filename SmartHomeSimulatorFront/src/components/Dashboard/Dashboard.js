import {
  Card, Col, Divider, Layout, Row, Switch, Typography,
} from 'antd'
import React, {useEffect} from 'react'
import {connect} from 'react-redux'
import {RoomCard, SimulationParameterCard, SimulationProfileCard} from '../index'
import './Dashboard.css'
import {getProfile, start, stop} from '../../services'

const Dashboard = ({simulationConfig, consoleMessage, dispatch}) => {
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
    </Card>
  )

  const {homeLayout, simulationRunning} = simulationConfig
  const {messages} = consoleMessage
  return (
    <Layout className='layout'>
      <Layout.Content className='content'>
        <Row type='flex' align='top'>
          <Col span={1} />
          <Col span={6}>
            <SimulationParameterCard simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            <SimulationProfileCard simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            {simulationSwitchCard}
          </Col>
          <Col span={1} />
          <Col span={15}>
            <Card title='Smart Home Core Functionality'>
              <Row gutter={[16, 16]}>
                {simulationRunning
                  ? homeLayout && homeLayout.roomList.map((room) => <Col span={8}>
                    <RoomCard key={room.name} room={room} fetchUserProfiles={fetchUserProfiles} />
                  </Col>)
                  : 'Simulation is off'}
              </Row>
            </Card>
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
