import { createStore, combineReducers } from "redux"
import { authenticationReducer } from "reducers/authentication"

export default createStore(combineReducers({authenticationState: authenticationReducer}))