import {combineReducers} from 'redux'
import simulationConfig from './simulationConfig'
import consoleMessage from './consoleMessage'

const rootReducer = combineReducers({simulationConfig, consoleMessage})

export default rootReducer
