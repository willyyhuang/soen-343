import {UserOutlined} from '@ant-design/icons'
import {
Card, Popover, Row, Typography,
} from 'antd'
import React from 'react'
import {ObjectIcon} from '../index'

const RoomCard = ({users, room, fetchUserProfiles}) => {
  const {name, objects} = room

  return (
    <Card
      extra={users.map((user) => (
        <Popover
          placement='bottom'
          content={<Typography.Text>{user.name}</Typography.Text>}>
          <UserOutlined />
        </Popover>
      ))}
      title={name}>
      <Row>
        {objects.length === 0
          ? 'This room does not have any object'
          : objects.map((item) => (
            <ObjectIcon
              roomName={name}
              object={item}
              fetchUserProfiles={fetchUserProfiles} />
            ))}
      </Row>
    </Card>
  )
}

RoomCard.displayName = 'RoomCard'
export default RoomCard
