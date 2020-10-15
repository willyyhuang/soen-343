import {
  Button, Card, Col, Divider, Layout, Row, Switch, Typography,
} from 'antd'
import React from 'react'
import {connect} from 'react-redux'
import {SimulationParameterCard, SimulationProfileCard} from '../index'
import './Dashboard.css'
import {getProfile} from '../../services'

const Dashboard = ({simulationConfig, authentication, dispatch}) => {
  const fetchUserProfiles = () => {
    getProfile({username: authentication.username}).then((response) => {
      const {data} = response
      const {currentSimulationProfile, simulationProfiles} = data
      dispatch({type: 'SET_SIMULATION_PROFILES', payload: simulationProfiles})
      dispatch({type: 'SET_CURRENT_SIMULATION_PROFILE', payload: currentSimulationProfile})
    })
  }

  const simulationSwitchCard = (
    <Card>
      <Row>
        <Col span={8}>
          <Switch className='item' />
          <Typography.Text>Simulation Mode</Typography.Text>
        </Col>
      </Row>
    </Card>
  )

  return (
    <Layout className='layout'>
      <Layout.Header>
        <Row>
          <Col push={23}>
            <Button onClick={() => dispatch({type: 'RESET_STATE'})}>Log Out</Button>
          </Col>
        </Row>
      </Layout.Header>
      <Layout.Content className='content'>
        <Row type='flex' align='middle'>
          <Col span={8} />
          <Col span={8}>
            <SimulationParameterCard />
            <Divider />
            <SimulationProfileCard simulationConfig={simulationConfig} authentication={authentication} fetchUserProfiles={fetchUserProfiles} />
            <Divider />
            {simulationSwitchCard}
          </Col>
          <Col span={8} />
        </Row>
      </Layout.Content>
    </Layout>
  )
}

const mapStateToProps = (state) => ({
  authentication: state.authentication,
  simulationConfig: state.simulationConfig,
})

Dashboard.displayName = 'Dashboard'
export default connect(mapStateToProps)(Dashboard)
