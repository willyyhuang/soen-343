import {combineReducers} from 'redux'
import authentication from './authentication'
import simulationConfig from './simulationConfig'

const rootReducer = combineReducers({authentication, simulationConfig})

export default rootReducer
