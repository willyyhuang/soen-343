import {combineReducers} from 'redux'
import authentication from './authentication'
import user from './user'

const rootReducer = combineReducers({authentication, user})

export default rootReducer
