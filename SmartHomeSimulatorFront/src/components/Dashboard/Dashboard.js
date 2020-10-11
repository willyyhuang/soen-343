import {
  Button, Card, Col, Divider, Form, InputNumber, Row, Switch, Typography, Upload,
} from 'antd'
import React from 'react'
import {connect} from 'react-redux'

const Dashboard = () => {
  const parameterCard = (
    <Card
      extra={<Upload>
        <Button>Upload House Layout File</Button>
      </Upload>}
      actions={[<Button>Save</Button>]}
      title='Set up simulation parameter'>
      <Form.Item label='Temperature Indoor (°C)'>
        <InputNumber />
      </Form.Item>
      <Form.Item label='Temperature Outdoor (°C)'>
        <InputNumber />
      </Form.Item>
    </Card>
  )
  const simulationSwitchCard = (
    <Card>
      <Row>
        <Col span={8}>
          <Switch style={{marginRight: 10}} />
          <Typography.Text>Simulation Mode</Typography.Text>
        </Col>
      </Row>
    </Card>
  )
  return (
    <Row type='flex' align='middle'>
      <Col span={8} />
      <Col style={{marginTop: '10%'}} span={8}>
        {parameterCard}
        <Divider />
        {simulationSwitchCard}
      </Col>
      <Col span={8} />
    </Row>
  )
}

const mapStateToProps = (state) => ({
  user: state.user,
})

Dashboard.displayName = 'Dashboard'
export default connect(mapStateToProps)(Dashboard)
