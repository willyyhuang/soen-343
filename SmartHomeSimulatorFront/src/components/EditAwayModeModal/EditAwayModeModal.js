import {
Form, InputNumber, Modal, Select,
} from 'antd'
import React from 'react'
import {setTimeBeforeAuthority} from '../../services'

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
          <Select mode='multiple'>
            {getLights().map((light) => <Select.Option value={light.name}>{light.name}</Select.Option>)}
          </Select>
        </Form.Item>
      </Modal>
    )
}

EditAwayModeModal.displayName = 'EditAwayModeModal'
export default EditAwayModeModal
