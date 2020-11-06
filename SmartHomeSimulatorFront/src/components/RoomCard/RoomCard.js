import {
  Card, Divider, Row, Switch, Table, Typography,
} from 'antd'
import React from 'react'
import {block, unblock} from '../../services'
import {ObjectIcon} from '../index'

const RoomCard = ({room, fetchUserProfiles}) => {
  const {name, objects} = room
  const windows = objects.filter((item) => item.objectType === 'WINDOW')
  // const columns = [
  // {title: 'ID', dataIndex: 'id'},
  // {
  // title: 'Status',
  // render: (data) => (
  // <Switch checked={data.status} />
  // ),
  // },
  // {
  // title: 'Block Window',
  // render: (data) => {
  // const {id} = data
  // const payload = {id, name}
  // return (
  // <Switch onChange={(value) => (value ? block(payload) && fetchUserProfiles() : unblock(payload) && fetchUserProfiles())} checked={data.blocked} />
  // )
  // },
  // },
  // ]

  return (
    <Card title={name}>
      {objects.map((item) => <ObjectIcon roomName={name} object={item} fetchUserProfiles={fetchUserProfiles} />)}
      {/* <Row justify='center'> */}
      {/* <Typography.Title level={5}>Windows</Typography.Title> */}
      {/* <Divider /> */}
      {/* </Row> */}
      {/* <Table columns={columns} dataSource={windows} pagination={false} /> */}
    </Card>
  )
}

RoomCard.displayName = 'RoomCard'
export default RoomCard
