import {Card} from 'antd'
import React from 'react'
import {ObjectIcon} from '../index'

const RoomCard = ({room, fetchUserProfiles}) => {
  const {name, objects} = room

  return (
    <Card title={name}>
      {objects.length === 0 ? 'This room does not have any object' : objects.map((item) => <ObjectIcon roomName={name} object={item} fetchUserProfiles={fetchUserProfiles} />)}
    </Card>
  )
}

RoomCard.displayName = 'RoomCard'
export default RoomCard
