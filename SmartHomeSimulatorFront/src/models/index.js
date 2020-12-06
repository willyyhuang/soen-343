import {combineReducers} from 'redux'
import simulationConfig from './simulationConfig'
import consoleMessage from './consoleMessage'
import zone from './zone'

const rootReducer = combineReducers({simulationConfig, consoleMessage, zone})

export default rootReducer
