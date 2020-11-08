import React from 'react'
import {Popover, Form, Switch} from 'antd'
import {
  block,
  unblock,
  turnOnLight,
  turnOffLight,
  openRoomWindow,
  closeRoomWindow,
  openRoomDoor,
  closeRoomDoor,
} from '../../services'
import './ObjectIcon.css'

const ObjectIcon = ({roomName, object, fetchUserProfiles}) => {
  const LightIconOff = 'https://img.icons8.com/ios/452/light-on.png'
  const LightIconOn = 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Light_bulb_%28yellow%29_icon.svg/1024px-Light_bulb_%28yellow%29_icon.svg.png'
  const DoorClosed = 'https://cdn1.iconfinder.com/data/icons/construction-2-13/48/61-512.png'
  const DoorOpened = 'https://image.flaticon.com/icons/png/512/59/59802.png'
  const WindowOpened = 'https://img.icons8.com/ios/452/open-window.png'
  const WindowClosed = 'https://icons.iconarchive.com/icons/iconsmind/outline/512/Window-icon.png'

  const {id, name, objectType} = object
  const payload = {id, name: roomName}
  let Icon
  let Content
  switch (objectType) {
    case 'LIGHT':
      Icon = object.status ? LightIconOn : LightIconOff
      Content = (
        <Switch
          onChange={(value) =>
            (value
              ? turnOnLight(payload) && fetchUserProfiles()
              : turnOffLight(payload) && fetchUserProfiles())}
          checked={object.status} />
      )
      break
    case 'DOOR':
      Icon = object.status ? DoorOpened : DoorClosed
      Content = (
        <Switch
          onChange={(value) =>
            (value
              ? openRoomDoor(payload) && fetchUserProfiles()
              : closeRoomDoor(payload) && fetchUserProfiles())}
          checked={object.status} />
      )
      break
    case 'WINDOW':
      Icon = object.status ? WindowOpened : WindowClosed
      Content = (
        <>
          <Form.Item label='Block with object'>
            <Switch
              onChange={(value) =>
                (value
                  ? block(payload) && fetchUserProfiles()
                  : unblock(payload) && fetchUserProfiles())}
              checked={object.blocked} />
          </Form.Item>
          <Form.Item label='Turn on/off'>
            <Switch
              onChange={(value) =>
                (value
                  ? openRoomWindow(payload) && fetchUserProfiles()
                  : closeRoomWindow(payload) && fetchUserProfiles())}
              disabled={object.blocked}
              checked={object.status} />
          </Form.Item>
        </>
      )
      break
    default:
      Icon = null
  }

  return (
    Icon && (
      <Popover
        content={Content}
        title={name}
        placement='bottom'
        trigger='click'>
        <img alt='objectIcon' className='image' width={50} src={Icon} />
      </Popover>
    )
  )
}

ObjectIcon.displayName = 'ObjectIcon'
export default ObjectIcon
