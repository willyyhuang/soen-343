import {
  Card, Col, Divider, Layout, Row, Switch, Typography,
} from 'antd'
import React, {useEffect} from 'react'
import {connect} from 'react-redux'
import {RoomCard, SimulationParameterCard, SimulationProfileCard} from '../index'
import './Dashboard.css'
import {getProfile, start, stop} from '../../services'

const Dashboard = ({simulationConfig, dispatch}) => {
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
        <Col span={8}>
          <Switch
            className='item'
            checked={simulationConfig.simulationRunning}
            onChange={(value) => (value ? start() && fetchUserProfiles() : stop() && fetchUserProfiles())} />
          <Typography.Text>Simulation Mode</Typography.Text>
        </Col>
      </Row>
    </Card>
  )

  const {homeLayout, simulationRunning} = simulationConfig
  return (
    <Layout className='layout'>
      <Layout.Header />
      <Layout.Content className='content'>
        <Row type='flex' align='top'>
          <Col span={2} />
          <Col span={8}>
            <SimulationParameterCard simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            <SimulationProfileCard simulationConfig={simulationConfig} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            {simulationSwitchCard}
          </Col>
          <Col span={2} />
          <Col span={10}>
            {simulationRunning && homeLayout && homeLayout.roomList.map((room) => <RoomCard room={room} fetchUserProfiles={fetchUserProfiles} />)}
          </Col>
          <Col span={2} />
        </Row>
      </Layout.Content>
    </Layout>
  )
}

const mapStateToProps = (state) => ({
  simulationConfig: state.simulationConfig,
})

Dashboard.displayName = 'Dashboard'
export default connect(mapStateToProps)(Dashboard)
