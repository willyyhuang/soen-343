import {
Card, Col, Row, Typography,
} from 'antd'
import React, {useState} from 'react'
import {DownOutlined, UpOutlined, AuditOutlined} from '@ant-design/icons'
import './OutputConsole.css'

const OutputConsole = ({messages}) => {
    const [isConsoleExpanded, setIsConsoleExpanded] = useState(true)

    if (!messages || messages.length === 0) return null
    return (
      <Card bodyStyle={{paddingBottom: 3, paddingTop: 3}} className='card'>
        <Row>
          <Col>
            <AuditOutlined />
            <Typography.Text className='bold'>Output Console</Typography.Text>
          </Col>
          <Col style={{marginLeft: 5}}>
            {isConsoleExpanded ? <DownOutlined onClick={() => setIsConsoleExpanded(!isConsoleExpanded)} /> : <UpOutlined onClick={() => setIsConsoleExpanded(!isConsoleExpanded)} />}
          </Col>
        </Row>
        {isConsoleExpanded ? messages.map((message) => (
          <Row>
            <Typography.Text>{message}</Typography.Text>
          </Row>
            )) : null}
      </Card>
    )
}

OutputConsole.displayName = 'OutputConsole'
export default OutputConsole
