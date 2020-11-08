import {
Form, Input, InputNumber, Modal, Select,
} from 'antd'
import React from 'react'
import {setTimeBeforeAuthority, setLights, setTimeToKeepLightOn} from '../../services'

const EditAwayModeModal = ({
visible, onClose, simulationConfig, fetchUserProfiles,
}) => {
    const {homeLayout, timeBeforeAuthoroties} = simulationConfig
    const {roomList} = homeLayout

    const getLights = () => {
        const lights = []
        // eslint-disable-next-line
        roomList.map((room) => {
        const {objects} = room
        // eslint-disable-next-line
            objects.map((item) => {
                if (item.objectType === 'LIGHT') {
                    lights.push(item)
                }
            })
        })
        return lights
    }

    return (
      <Modal footer={null} visible={visible} onCancel={onClose}>
        <Form.Item label='Authority Alert Time (s)'>
          <InputNumber
            value={timeBeforeAuthoroties}
            onChange={(value) => setTimeBeforeAuthority(value) && fetchUserProfiles()} />
        </Form.Item>
        <Form.Item label='Lights to Remain on During Away Mode'>
          <Select mode='multiple' onChange={(value) => setLights(value) && fetchUserProfiles()}>
            {getLights().map((light) => <Select.Option value={light.id}>{light.name}</Select.Option>)}
          </Select>
        </Form.Item>
        <Form.Item label='Time to Keep Light On (HH:MM to HH:MM)'>
          <Input
            onBlur={(e) => setTimeToKeepLightOn(e.target.value) && fetchUserProfiles()} />
        </Form.Item>
      </Modal>
    )
}

EditAwayModeModal.displayName = 'EditAwayModeModal'
export default EditAwayModeModal
