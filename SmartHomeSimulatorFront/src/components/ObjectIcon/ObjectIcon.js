import React from 'react'
import {Popover, Switch} from 'antd'
import {
  block, unblock, on, off,
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
      Icon = object.on ? LightIconOn : LightIconOff
      Content = <Switch onChange={(value) => (value ? on(payload) && fetchUserProfiles() : off(payload) && fetchUserProfiles())} checked={object.on} />
      break
    case 'DOOR':
      Icon = object.blocked ? DoorClosed : DoorOpened
      Content = <Switch checked={object.blocked} />
      break
    case 'WINDOW':
      Icon = object.blocked ? WindowClosed : WindowOpened
      Content = <Switch onChange={(value) => (value ? block(payload) && fetchUserProfiles() : unblock(payload) && fetchUserProfiles())} checked={object.blocked} />
      break
    default:
      Icon = null
  }

  return Icon && (
    <Popover content={Content} title={name} placement='bottom' trigger='click'>
      <img className='image' width={50} src={Icon} />
    </Popover>
  )
}

ObjectIcon.displayName = 'ObjectIcon'
export default ObjectIcon
