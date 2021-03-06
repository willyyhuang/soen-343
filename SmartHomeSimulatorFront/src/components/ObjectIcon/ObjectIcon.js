import React from 'react'
import {
Popover, Form, Switch, message,
} from 'antd'
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

const ObjectIcon = ({
  addConsoleMessage,
  roomName,
  object,
  fetchUserProfiles,
}) => {
  const LightIconOff = 'https://img.icons8.com/ios/452/light-on.png'
  const LightIconOn = 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Light_bulb_%28yellow%29_icon.svg/1024px-Light_bulb_%28yellow%29_icon.svg.png'
  const DoorClosed = 'https://cdn1.iconfinder.com/data/icons/construction-2-13/48/61-512.png'
  const DoorOpened = 'https://image.flaticon.com/icons/png/512/59/59802.png'
  const WindowOpened = 'https://img.icons8.com/ios/452/open-window.png'
  const WindowClosed = 'https://icons.iconarchive.com/icons/iconsmind/outline/512/Window-icon.png'
  const ACOn = 'https://i.imgur.com/eANFHBB.png'
  const ACOff = 'https://i.imgur.com/2jGVfN0.png'
  const HeaterOn = 'https://i.imgur.com/oTeONVB.png'
  const HeaterOff = 'https://i.imgur.com/OeGtwdx.png'

  const alertUserAfterTime = (seconds) => {
    const milliseconds = seconds * 1000
    const notification = '[Alert] Police is notified.'
    setTimeout(() => {
      addConsoleMessage(notification)
      message.warning(notification)
    }, milliseconds)
  }

  const awayModeNotification = (timeBeforeAuthorities) => {
    const notification = `[Alert] Police will be notified in ${timeBeforeAuthorities} seconds.`
    addConsoleMessage(notification)
    message.warning(notification)
    alertUserAfterTime(timeBeforeAuthorities)
  }

  const {id, name, objectType} = object

  const payload = {id, name: roomName}
  let Icon
  let Content
  switch (objectType) {
    case 'AC':
      Icon = object.status ? ACOn : ACOff
      Content = 'This object is not controllable'
      break
    case 'HEATER':
      Icon = object.status ? HeaterOn : HeaterOff
      Content = 'This object is not controllable'
      break
    case 'LIGHT':
      Icon = object.status ? LightIconOn : LightIconOff
      Content = (
        <Switch
          onChange={(value) =>
            (value
              ? turnOnLight(payload).then((response) => {
                const {data} = response
                addConsoleMessage(data.consoleMessage)
                if (data.awayMode) {
                  awayModeNotification(data.timeBeforeAuthorities)
                }
              }) && fetchUserProfiles()
              : turnOffLight(payload).then((response) => {
                const {data} = response
                addConsoleMessage(data.consoleMessage)
                if (data.awayMode) {
                  awayModeNotification(data.timeBeforeAuthorities)
                }
              }) && fetchUserProfiles())}
          checked={object.status} />
      )
      break
    case 'DOOR':
      Icon = object.status ? DoorOpened : DoorClosed
      Content = (
        <Switch
          onChange={(value) =>
            (value
              ? openRoomDoor(payload).then((response) => {
                const {data} = response
                addConsoleMessage(data.consoleMessage)
                if (data.awayMode) {
                  awayModeNotification(data.timeBeforeAuthorities)
                }
              }) && fetchUserProfiles()
              : closeRoomDoor(payload).then((response) => {
                const {data} = response
                addConsoleMessage(data.consoleMessage)
                if (data.awayMode) {
                  awayModeNotification(data.timeBeforeAuthorities)
                }
              }) && fetchUserProfiles())}
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
                  ? block(payload).then((response) => {
                    const {data} = response
                    addConsoleMessage(data.consoleMessage)
                    if (data.awayMode) {
                      awayModeNotification(data.timeBeforeAuthorities)
                    }
                  }) && fetchUserProfiles()
                  : unblock(payload).then((response) => {
                    const {data} = response
                    addConsoleMessage(data.consoleMessage)
                    if (data.awayMode) {
                      awayModeNotification(data.timeBeforeAuthorities)
                    }
                  }) && fetchUserProfiles())}
              checked={object.blocked} />
          </Form.Item>
          <Form.Item label='Open/Close'>
            <Switch
              onChange={(value) =>
                (value
                  ? openRoomWindow(payload).then((response) => {
                    const {data} = response
                    addConsoleMessage(data.consoleMessage)
                    if (data.awayMode) {
                      awayModeNotification(data.timeBeforeAuthorities)
                    }
                  }) && fetchUserProfiles()
                  : closeRoomWindow(payload).then((response) => {
                    const {data} = response
                    addConsoleMessage(data.consoleMessage)
                    if (data.awayMode) {
                      awayModeNotification(data.timeBeforeAuthorities)
                    }
                  }) && fetchUserProfiles())}
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
